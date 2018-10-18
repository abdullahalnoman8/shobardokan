package com.tp365.shobardokan.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public String findRoleNameById(Integer roleId) {

        String query = "SELECT name FROM role WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{roleId}, String.class);
        } catch (DataAccessException e) {
            log.error("Getting Role Name Failed With Query: {}. Error: {}", query, e.getMessage());
            return "";
        }
    }
}