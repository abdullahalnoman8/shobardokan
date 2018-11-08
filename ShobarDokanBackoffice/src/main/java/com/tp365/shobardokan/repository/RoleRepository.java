package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.enums.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Slf4j
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String findRoleNameById(Integer userId) {

        String query = "SELECT name FROM role WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{userId}, String.class);
        } catch (DataAccessException e) {
            log.error("Getting Role Name Failed With Query: {}. Error: {}", query, e.getMessage());
            return "";
        }
    }

    public Role findByName(String uname) {
        String query = "SELECT * FROM role WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{uname}, new RowMapper<Role>() {
                @Override
                public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                    Role role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setRole(Roles.valueOf(resultSet.getString("name")));
                    return role;
                }
            });
        } catch (DataAccessException e) {
            log.error("Getting Role Name Failed With Query: {}. Error: {}", query, e.getMessage());
            return null;
        }
    }
}