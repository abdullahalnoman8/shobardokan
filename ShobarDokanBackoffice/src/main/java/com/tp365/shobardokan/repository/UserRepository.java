package com.tp365.shobardokan.repository;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.lang.Nullable;

@Slf4j
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
        if(user.getUsername() == null){
            return user;
        }
        Map<String,Object> parameterMap = new HashMap<>();
        parameterMap.put("username",user.getUsername());
        parameterMap.put("phone",user.getPhone());
        parameterMap.put("email",user.getEmail());
        parameterMap.put("password",user.getPassword());
        parameterMap.put("user_status", user.getUserStatus());
        parameterMap.put("is_active",user.getIsActive());
        parameterMap.put("last_active",user.getLastActiveDate());
        parameterMap.put("created_date",user.getCreatedDate());


        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterMap);

            if(autoGenId !=null){
                user.setId(autoGenId.intValue());
                log.info("User Added With ID {}",autoGenId);
                return user;
            }

        }catch (DataAccessException dae){
            log.error(dae.getMessage());
            log.info("User did not added: {}",dae.getLocalizedMessage());
            return user;
        }

        return user;
    }

    public List<User> findAll(){
        String query = "Select * from user";

        try{
           return  jdbcTemplate.query(query,new UserRowMapper());
        }catch (DataAccessException dae){
            dae.printStackTrace();
            dae.getLocalizedMessage();
        }
        return new ArrayList<>();
    }

    public User findUserByUserName(String userName) {
        String query ="SELECT * FROM user WHERE BINARY UserName = ? ";
        try{
            return jdbcTemplate.queryForObject(query,new Object[]{userName},new UserRowMapper());
        }catch (DataAccessException dae){
            dae.printStackTrace();
            dae.getLocalizedMessage();
            return new User();
        }
    }


    class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setUserStatus(User.UserStatus.valueOf("user_status"));
            user.setIsActive(resultSet.getBoolean("is_active"));
            user.setLastActiveDate(Utils.convertDateToTimeStamp(resultSet.getDate("datetime")));
            user.setCreatedDate(Utils.convertTimeStampToDate(resultSet.getTimestamp("created_date")));
            return user;
        }
    }

}