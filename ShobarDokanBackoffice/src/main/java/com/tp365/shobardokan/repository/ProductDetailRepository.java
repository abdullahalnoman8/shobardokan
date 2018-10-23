package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.ProductDetail;
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

    public ProductDetail add(ProductDetail productDetail){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product_details")
                .usingGeneratedKeyColumns("id");
        Map<String,Object> parameterData = new HashMap<>();
        parameterData.put("category_id",productDetail.getCategory().getId());
        parameterData.put("request_id",productDetail.getRequestId());
        parameterData.put("url",productDetail.getUrl());
        parameterData.put("product_description",productDetail.getProductDescription());
        parameterData.put("unit_price",productDetail.getUnitPrice());
        parameterData.put("delivery_fee",productDetail.getDeliveryFee());
        parameterData.put("status",productDetail.getStatus());
        parameterData.put("cost_price",productDetail.getCostPrice());
        parameterData.put("estimated_delivery_date",new Date());
        parameterData.put("comments",productDetail.getComments());
        parameterData.put("user_id",productDetail.getUser().getId());

        try{
            Number autoGenId = simpleJdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId != null){
                productDetail.setId(autoGenId.intValue());
                log.info("Product Details Added with ID {}",autoGenId);
                log.info("Product Details Added with Data {}",productDetail.toString());
                return productDetail;
            }
        }catch (DataAccessException dae){
            log.error(dae.getMessage());
            log.info("Product Detail did not added: {}",dae.getLocalizedMessage());
            return productDetail;
        }
        return productDetail;
    }
}