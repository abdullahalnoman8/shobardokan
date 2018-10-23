package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class VerificationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Verification add(Verification verification){

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("verification")
                .usingGeneratedKeyColumns("id");
        if(verification == null){
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("code",verification.getCode());
        parameterData.put("type",verification.getType());
        parameterData.put("code_sent_at",verification.getCodeSentDate());
        parameterData.put("code_sent_through",verification.getCodeSentThrough());
        parameterData.put("user_id",verification.getUser().getId());
        parameterData.put("valid_till",verification.getValidTillDate());
        parameterData.put("is_successful",verification.getIsSuccessful());


        try{
            Number autoGenId = simpleJdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId != null){
                verification.setId(autoGenId.intValue());
                return verification;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            log.info("Data added failed in Verification Table Error: {}",dae.getLocalizedMessage());
            return verification;
        }

        return verification;
    }
}