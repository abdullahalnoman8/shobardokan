package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class UserRoleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRole add(UserRole userRole){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users_roles")
                .usingGeneratedKeyColumns("id");

        HashMap<String,Object> parametersData = new HashMap<>();
        parametersData.put("user_id",userRole.getUser().getId());
        parametersData.put("role_id",userRole.getRoleId());
        parametersData.put("created_at",userRole.getCreatedDate());
        parametersData.put("deleted_at",userRole.getDeletedDate());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parametersData);
            if(autoGenId !=null){
                userRole.setId(autoGenId.intValue());
                return userRole;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            log.error("User Role Info Add Failed, Error: {}",dae.getLocalizedMessage());
            return userRole;
        }
        return userRole;
    }
}