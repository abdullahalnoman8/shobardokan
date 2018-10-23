package com.tp365.shobardokan.repository;

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
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestImageRepository {
    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void testImageAdd() {
        Image image = new Image();
        image.setFileName("test_img");

        // Test Save
        Image savedImage = imageRepository.add(image);
        Assert.assertNotNull(savedImage);
        Assert.assertNotNull(savedImage.getId());
        log.info("Saved Image: {}", savedImage.toString());


    }
}