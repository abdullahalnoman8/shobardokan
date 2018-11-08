package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.model.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ProductDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProductDetails add(ProductDetails productDetails) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product_details")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameterData = new HashMap<>();
        parameterData.put("category_id", productDetails.getCategory().getId());
        parameterData.put("request_id", productDetails.getUserRequest().getId());
        parameterData.put("url", productDetails.getUrl());
        parameterData.put("product_description", productDetails.getProductDescription());
        parameterData.put("unit_price", productDetails.getUnitPrice());
        parameterData.put("delivery_fee", productDetails.getDeliveryFee());
        parameterData.put("status", productDetails.getStatus().name());
        parameterData.put("cost_price", productDetails.getCostPrice());
        parameterData.put("estimated_delivery_date", productDetails.getEstimatedDeliveryDate());
        parameterData.put("comments", productDetails.getComments());
        parameterData.put("user_id", productDetails.getUser().getId());
        try {
            Number autoGenId = simpleJdbcInsert.executeAndReturnKey(parameterData);
            if (autoGenId != null) {
                productDetails.setId(autoGenId.intValue());
                log.info("Product Details Added with ID {}, And Data {}", autoGenId,productDetails.toString());
                return productDetails;
            }
        } catch (DataAccessException dae) {
            log.info("Product Detail did not added: {}", dae.getLocalizedMessage());
            return productDetails;
        }
        return productDetails;
    }

    public ProductDetails findById(Integer id) {
        String query = "SELECT * from product_details where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new ProductDetailsRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Data Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return null;
    }


    public List<ProductDetails> findAll() {
        String query = "SELECT * from product_details";
        try {
            return jdbcTemplate.query(query, new ProductDetailsRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Data Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public List<UserRequest> requestedProductList() {
        String query = "SELECT * FROM  user_requests  LEFT JOIN product_details ON user_requests.id \n" +
                "= product_details.request_id WHERE product_details.request_id IS NULL";
        try {
            return jdbcTemplate.query(query, new UserRequestRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Data Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public UserRequest requestedUncheckedProductById(Integer id) {
        String query = "SELECT * FROM  user_requests  WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new UserRequestRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Data Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return null;
    }

    public boolean update(ProductDetails productDetails) {
        String query = "UPDATE product_details SET request_id = ?, category_id = ?, url = ?, " +
                "product_description = ?, unit_price = ?, delivery_fee = ?, status = ?, cost_price = ?, " +
                " estimated_delivery_date = ?, comments = ?, user_id = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(query, productDetails.getUserRequest().getId(), productDetails.getCategory().getId(),
                    productDetails.getUrl(), productDetails.getProductDescription(), productDetails.getUnitPrice(),
                    productDetails.getDeliveryFee(), productDetails.getStatus().name(), productDetails.getCostPrice(),
                    productDetails.getEstimatedDeliveryDate(), productDetails.getComments(),
                    productDetails.getUser().getId(), productDetails.getId()) == 1;
        } catch (DataAccessException e) {
            log.error("Update failed for object: {}. Error: {}", productDetails, e.getLocalizedMessage());
            return false;
        }
    }


    private class ProductDetailsRowMapper implements RowMapper<ProductDetails> {
        @Override
        public ProductDetails mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            ProductDetails productDetails = new ProductDetails();
            productDetails.setId(resultSet.getInt("id"));
            UserRequest userRequest = new UserRequest();
            userRequest.setId(resultSet.getInt("request_id"));
            productDetails.setUserRequest(userRequest);
            Category category = new Category();
            category.setId(resultSet.getInt("category_id"));
            productDetails.setCategory(category);
            productDetails.setUrl(resultSet.getString("url"));
            productDetails.setProductDescription(resultSet.getString("product_description"));
            productDetails.setUnitPrice(resultSet.getDouble("unit_price"));
            productDetails.setDeliveryFee(resultSet.getDouble("delivery_fee"));
            productDetails.setStatus(Status.valueOf(resultSet.getString("status")));
            productDetails.setCostPrice(resultSet.getDouble("cost_price"));
            productDetails.setEstimatedDeliveryDate(resultSet.getDate("estimated_delivery_date"));
            productDetails.setComments(resultSet.getString("comments"));
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            productDetails.setUser(user);
            return productDetails;
        }
    }

    private class UserRequestRowMapper implements RowMapper<UserRequest> {
        @Override
        public UserRequest mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserRequest userRequest = new UserRequest();
            userRequest.setId(resultSet.getInt("id"));
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            userRequest.setUser(user);
            userRequest.setProductUrl(resultSet.getString("product_url"));
            userRequest.setComments(resultSet.getString("comments"));
            return userRequest;
        }
    }
}