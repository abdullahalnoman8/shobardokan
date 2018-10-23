package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestCategoryRepository {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testAdd(){
        Category category = new Category();
        Image image = new Image();
        image.setId(2);
        category.setImage(image);
        category.setName("Test Category Name");

        Category savedCategory = categoryRepository.add(category);
        Assert.assertNotNull(savedCategory.getId());
        log.info("Added Category Detail: {}",savedCategory);

    }
}