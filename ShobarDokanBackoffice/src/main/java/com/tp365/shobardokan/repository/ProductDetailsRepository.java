package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        parameterData.put("status", productDetails.getStatus());
        parameterData.put("cost_price", productDetails.getCostPrice());
        parameterData.put("estimated_delivery_date", new Date());
        parameterData.put("comments", productDetails.getComments());
        parameterData.put("user_id", productDetails.getUser().getId());

        try {
            Number autoGenId = simpleJdbcInsert.executeAndReturnKey(parameterData);
            if (autoGenId != null) {
                productDetails.setId(autoGenId.intValue());
                log.info("Product Details Added with ID {}", autoGenId);
                log.info("Product Details Added with Data {}", productDetails.toString());
                return productDetails;
            }
        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.info("Product Detail did not added: {}", dae.getLocalizedMessage());
            return productDetails;
        }
        return productDetails;
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
            productDetails.setStatus(ProductDetails.Status.valueOf(resultSet.getString("status")));
            productDetails.setCostPrice(resultSet.getDouble("cost_pricedouble"));
            productDetails.setDeliveryFee(resultSet.getDouble("estimated_delivery_datedate"));
            productDetails.setComments(resultSet.getString("commentstext"));
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            productDetails.setUser(user);
            return productDetails;
        }
    }
}