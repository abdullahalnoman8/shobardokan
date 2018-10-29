package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

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
}