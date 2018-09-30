package com.tp365.shobardokan.repository;


import com.sun.istack.internal.Nullable;
import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.lang.Nullable;

@Slf4j
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


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
        @Nullable
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("ID"));
            user.setUsrName(resultSet.getString("Username"));
            user.setPassword(resultSet.getString("Password"));
            user.setFirstName(resultSet.getString("First Name"));
            user.setMiddleName(resultSet.getString("Middle Name"));
            user.setLastName(resultSet.getString("Last Name"));
            Role role = new Role();
            role.setId(resultSet.getInt("role_id"));
            user.setRole(role);
            return user;
        }
    }

}