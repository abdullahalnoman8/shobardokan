package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
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
public class CategoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Category add(Category category){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("category")
                .usingGeneratedKeyColumns("id");

        if(category.getName() == null){
            log.info("Category Information: {}",category);
            return null;
        }
        HashMap<String,Object> parameterData = new HashMap<>();

        parameterData.put("name",category.getName());
        parameterData.put("image_id",category.getImage().getId());
        parameterData.put("created_date",category.getCreatedAt());

        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                category.setId(autoGenId.intValue());
                return category;
            }
        }catch (DataAccessException dae){
            dae.getLocalizedMessage();
            log.error("Category Information Adding Failed, Error: {}",dae.getLocalizedMessage());
            return category;
        }
        return category;
    }


    public List<Category> getProductCategoryList() {
        String query = "SELECT * from category";
        try {
            return jdbcTemplate.query(query, new CategoryRowMaper());
        } catch (DataAccessException dae) {
            log.error("User Data Not Found, Error: {}", dae.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    private class CategoryRowMaper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setName(resultSet.getString("name"));
            return category;
        }
    }
}