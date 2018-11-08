package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class   CartItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CartItem add(CartItem cartItem){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_items")
                .usingGeneratedKeyColumns("id");

        if(cartItem.getProduct().getId() == null){
            log.info("User Details value is: {}",cartItem);
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("cart_id",cartItem.getCart().getId());
        parameterData.put("product_id",cartItem.getProduct().getId());
        parameterData.put("unit_price",cartItem.getUnitPrice());
        parameterData.put("unit_cost",cartItem.getUnitCost());
        parameterData.put("quantity",cartItem.getQuantity());
        parameterData.put("tax_amount",cartItem.getTaxAmount());
        parameterData.put("total_amount",cartItem.getTotalAmount());
        parameterData.put("total_cost",cartItem.getTotalCost());
        parameterData.put("created_date",cartItem.getCreatedAt());
        parameterData.put("updated_at",cartItem.getUpdatedAt());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                cartItem.setId(autoGenId.intValue());
                return cartItem;
            }
        }catch (DataAccessException dae){
            log.error("Cart Item Adding Failed, Error: {}",dae.getLocalizedMessage());
            return cartItem;
        }

        return cartItem;
    }
}