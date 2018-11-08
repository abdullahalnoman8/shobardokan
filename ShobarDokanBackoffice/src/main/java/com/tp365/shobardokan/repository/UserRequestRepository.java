package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRequest;
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

@Slf4j
@Repository
public class UserRequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRequest save(UserRequest userRequest) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_requests")
                .usingGeneratedKeyColumns("id");
        if (userRequest.getUser() == null) {
            return userRequest;
        }
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("user_id", userRequest.getUser().getId());
        parameterMap.put("product_url", userRequest.getProductUrl());
        parameterMap.put("quantity", userRequest.getQuantity());
        parameterMap.put("status", userRequest.getStatus());
        parameterMap.put("created_at", userRequest.getCreatedAt());
        parameterMap.put("updated_at", userRequest.getUpdatedAt());
        parameterMap.put("comments", userRequest.getComments());

        try {
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterMap);

            if (autoGenId != null) {
                userRequest.setId(autoGenId.intValue());
                log.info("UserRequest added with id:  {}", autoGenId);
                return userRequest;
            }

        } catch (DataAccessException dae) {
            log.error("Error Found %s And Error Message",UserRepository.class.getName(),dae.getMessage());
            log.info("UserRequest did not added: {}", dae.getLocalizedMessage());
            return userRequest;
        }
        return userRequest;
    }

    public List<UserRequest> findAll() {
        String query = "SELECT username, user_requests.* FROM user INNER JOIN " +
                "user_requests ON user.id = user_requests.user_id";
        try {
            return jdbcTemplate.query(query, new UserRequestRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Request Product Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public List<UserRequest> findAllRequestedProductByUserId(int userId) {
        String query = "SELECT username, user_requests.* FROM user INNER JOIN " +
                "user_requests ON user.id = user_requests.user_id WHERE status='OPEN' and user_id = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{userId}, new UserRequestRowMapper());
        } catch (DataAccessException dae) {
            log.error("User Request Product Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public boolean updateUserRequestStatus(Integer id) {
        String query = "UPDATE user_requests SET status = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(query, UserRequest.Status.CLOSED.name(), id) == 1;
        } catch (DataAccessException e) {
            log.error("Update failed for object: {}. Error: {}", id, e.getLocalizedMessage());
            return false;
        }
    }

    class UserRequestRowMapper implements RowMapper<UserRequest> {
        @Override
        public UserRequest mapRow(ResultSet rs, int i) throws SQLException {
            UserRequest userRequest = new UserRequest();
            userRequest.setId(rs.getInt("id"));
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            userRequest.setUser(user);
            userRequest.setProductUrl(rs.getString("product_url"));
            userRequest.setQuantity(rs.getInt("quantity"));
            userRequest.setStatus(UserRequest.Status.valueOf(rs.getString("status")));
            userRequest.setCreatedAt(rs.getDate("created_at"));
            userRequest.setUpdatedAt(rs.getDate("updated_at"));
            userRequest.setComments(rs.getString("comments"));
            return userRequest;
        }
    }

}