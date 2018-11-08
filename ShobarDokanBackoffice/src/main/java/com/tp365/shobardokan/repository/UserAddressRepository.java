package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class UserAddressRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserAddress add(UserAddress userAddress){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_addresses")
                .usingGeneratedKeyColumns("id");

        HashMap<String,Object> parametersData = new HashMap<>();
        parametersData.put("street_address_1",userAddress.getStreetAddressOne());
        parametersData.put("street_address_2",userAddress.getStreetAddressTwo());
        parametersData.put("city",userAddress.getCity());
        parametersData.put("state",userAddress.getState());
        parametersData.put("zip_code",userAddress.getZipCode());
        parametersData.put("user_id",userAddress.getUser().getId());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parametersData);
            if(autoGenId !=null){
                userAddress.setId(autoGenId.intValue());
                return userAddress;
            }
        }catch (DataAccessException dae){
            log.error("User Address Info Add Failed, Error: {}",dae.getLocalizedMessage());
            return userAddress;
        }
        return userAddress;
    }
}