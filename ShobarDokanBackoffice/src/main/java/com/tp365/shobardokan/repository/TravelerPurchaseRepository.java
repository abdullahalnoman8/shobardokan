package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.TravelerPurchase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class TravelerPurchaseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TravelerPurchase add(TravelerPurchase travelerPurchase){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("purchase_by_traveler")
                .usingGeneratedKeyColumns("id");

        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("user_id",travelerPurchase.getUser().getId());
        parameterData.put("requested_product_id",travelerPurchase.getRequestedProductId());
        parameterData.put("requested_status",travelerPurchase.getRequestedStatus());
        parameterData.put("purchase_invoice_image_id",travelerPurchase.getPurchaseInvoiceImageId());
        parameterData.put("estimated_date",travelerPurchase.getEstimatedDate());
        parameterData.put("delivered_date",travelerPurchase.getDeliveryDate());
        parameterData.put("created_date",travelerPurchase.getCreatedDate());
        parameterData.put("comments",travelerPurchase.getComments());
        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                travelerPurchase.setId(autoGenId.intValue());
                return travelerPurchase;
            }
        }catch (DataAccessException dae){
            log.error("Traveler purchase Info Adding Failed, Error: {}",dae.getLocalizedMessage());
            return travelerPurchase;
        }
        return travelerPurchase;
    }
}