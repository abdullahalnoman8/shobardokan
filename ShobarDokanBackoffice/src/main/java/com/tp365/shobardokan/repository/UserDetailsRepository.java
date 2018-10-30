package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class UserDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDetails add(UserDetails userDetails){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_details")
                .usingGeneratedKeyColumns("id");

        if(userDetails == null){
            log.info("User Details value is: {}",userDetails);
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("user_id",userDetails.getUser().getId());
        parameterData.put("first_name",userDetails.getFirst_name());
        parameterData.put("last_name",userDetails.getLast_name());
        parameterData.put("gender",userDetails.getGender());
        parameterData.put("date_of_birth",userDetails.getDate_of_birth());
        parameterData.put("present_address",userDetails.getPresent_address());
        parameterData.put("mailing_address",userDetails.getMailing_address());
        parameterData.put("emergency_contact_no",userDetails.getEmergency_contact_number());
        parameterData.put("created_date",userDetails.getCreatedDate());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId != null){
                userDetails.setId(autoGenId.intValue());
                return userDetails;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            return userDetails;
        }
        return userDetails;
    }

}