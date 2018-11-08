package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.ProductDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class ProductDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProductDetails add(ProductDetails productDetails){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product_details")
                .usingGeneratedKeyColumns("id");
        Map<String,Object> parameterData = new HashMap<>();
        parameterData.put("category_id", productDetails.getCategory().getId());
        parameterData.put("request_id", productDetails.getUserRequest().getId());
        parameterData.put("url", productDetails.getUrl());
        parameterData.put("product_description", productDetails.getProductDescription());
        parameterData.put("unit_price", productDetails.getUnitPrice());
        parameterData.put("delivery_fee", productDetails.getDeliveryFee());
        parameterData.put("status", productDetails.getStatus());
        parameterData.put("cost_price", productDetails.getCostPrice());
        parameterData.put("estimated_delivery_date",new Date());
        parameterData.put("comments", productDetails.getComments());
        parameterData.put("user_id", productDetails.getUser().getId());

        try{
            Number autoGenId = simpleJdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId != null){
                productDetails.setId(autoGenId.intValue());
                log.info("Product Details Added with ID {}, And Data: {}",autoGenId,productDetails.toString());
                return productDetails;
            }
        }catch (DataAccessException dae){
            log.info("Product Detail did not added: {}",dae.getLocalizedMessage());
            return productDetails;
        }
        return productDetails;
    }
}