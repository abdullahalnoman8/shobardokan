package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UsersRoles;
import com.tp365.shobardokan.model.enums.Roles;
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

@Slf4j
@Repository
public class UsersRolesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UsersRoles add(UsersRoles usersRoles){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users_roles")
                .usingGeneratedKeyColumns("id");

        HashMap<String,Object> parametersData = new HashMap<>();
        parametersData.put("user_id", usersRoles.getUser().getId());
        parametersData.put("role_id", usersRoles.getRole().getId());
        parametersData.put("created_at", usersRoles.getCreatedAt());
        parametersData.put("deleted_at", usersRoles.getDeletedAt());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parametersData);
            if(autoGenId !=null){
                usersRoles.setId(autoGenId.intValue());
                return usersRoles;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            log.error("User Role Info Add Failed, Error: {}",dae.getLocalizedMessage());
            return usersRoles;
        }
        return usersRoles;
    }

    public List<Role> resolveRolesForUser(User user) {
        String query = "SELECT ur.*, r.name FROM users_roles ur\n" +
                "INNER JOIN role r\n" +
                "ON r.id = ur.`role_id`\n" +
                "WHERE ur.user_id = ? AND ur.deleted_at IS NULL\n";

        try {
            return jdbcTemplate.query(query, new Object [] {user.getId()}, new RowMapper<Role>() {
                @Override
                public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Role role = new Role();
                    role.setRole(Roles.valueOf(rs.getString("name")));
                    return role;
                }
            });
        } catch (DataAccessException dae) {
            log.error("Failed to execute the following query:");
            log.error(query.replace("?", "{}"), user.getId());
            log.error(dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }


}