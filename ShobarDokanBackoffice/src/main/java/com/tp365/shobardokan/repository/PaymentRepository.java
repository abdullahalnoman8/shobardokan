package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class PaymentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Payment add(Payment payment){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("payment")
                .usingGeneratedKeyColumns("id");

        if(payment.getCart().getId() == null){
            log.info("Cart Not Found For Payment: {}",payment.toString());
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("cart_id",payment.getCart().getId());
        parameterData.put("paid_amount",payment.getPaidAmount());
        parameterData.put("payment_method",payment.getPaymentMethod());
        parameterData.put("is_confirmed",payment.getIsConfirmed());
        parameterData.put("token",payment.getToken());
        parameterData.put("created_at",payment.getCreatedDate());
        parameterData.put("updated_at",payment.getUpdatedDate());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                payment.setId(autoGenId.intValue());
                return payment;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            log.error("Payment Failed, Error: {}",dae.getLocalizedMessage());
            return payment;
        }

        return payment;
    }
}