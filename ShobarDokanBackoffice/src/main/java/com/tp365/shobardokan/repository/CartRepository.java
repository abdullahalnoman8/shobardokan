package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class CartRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cart add(Cart cart){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");

        if(cart.getUser().getId() == null){
            log.info("Cart Information: {}",cart);
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("user_id",cart.getUser().getId());
        parameterData.put("promo_code",cart.getPromoCode());
        parameterData.put("delivery_fee",cart.getDeliveryFee());
        parameterData.put("total_amount",cart.getTotalAmount());
        parameterData.put("discount_amount",cart.getDiscountAmount());
        parameterData.put("discount_type",cart.getDiscountType());
        parameterData.put("paid_amount",cart.getPaidAmount());
        parameterData.put("due_amount",cart.getDueAmount());
        parameterData.put("delivery_date",cart.getDeliveryAt());
        parameterData.put("user_addresses_id",cart.getUserAddress().getId());
        parameterData.put("is_confirmed",cart.getIsConfirmed());
        parameterData.put("is_complete",cart.getIsComplete());
        parameterData.put("is_canceled",cart.getIsCanceled());
        parameterData.put("status",cart.getStatus());
        parameterData.put("updated_at",cart.getUpdatedAt());
        parameterData.put("delivered_at",cart.getDeliveryAt());
        parameterData.put("deleted_at",cart.getDeletedAt());
        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                cart.setId(autoGenId.intValue());
                return cart;
            }
        }catch (DataAccessException dae){
            log.error("Cart Information Adding Failed, Error: {}",dae.getLocalizedMessage());
            return cart;
        }
        return cart;
    }
}