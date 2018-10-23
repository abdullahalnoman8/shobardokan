package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class ImageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Image add(Image image) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("image")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", image.getData());
        parameters.put("image_hash", image.getImageHash());
        parameters.put("file_name", image.getFileName());
        parameters.put("file_size", image.getFileSize());
        parameters.put("file_type", image.getFileType());
        parameters.put("created_date", new Date());

        log.info("Adding Image with Parameter: {}",parameters.toString());
        try{
            Number genId = jdbcInsert.executeAndReturnKey(parameters);
            if (genId != null) {
                image.setId(genId.intValue());
            }
        }catch (DataAccessException dae){
            log.error("Method Name: {}, at Image Found Error: {}","add",dae.getLocalizedMessage());
        }
        return image;
    }
}