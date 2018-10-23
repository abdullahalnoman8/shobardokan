package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.model.ProductDetail;
import com.tp365.shobardokan.model.User;
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
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProductDetailRepository {


    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Test
    public void testAdd(){
        Category category = new Category();
        category.setId(1);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setCategory(category);
        productDetail.setRequestId(1);
        productDetail.setUrl("https:www.abc.com");
        productDetail.setProductDescription("Test product Description");
        productDetail.setUnitPrice(89.63);
        productDetail.setDeliveryFee(15.89);
        productDetail.setStatus(ProductDetail.Status.ACCEPTED);
        productDetail.setCostPrice(789.63);
        productDetail.setComments("Test Comment");
        User user = new User();
        user.setId(3);
        productDetail.setUser(user);

        ProductDetail savedProductDetail = productDetailRepository.add(productDetail);
        Assert.assertNotNull(savedProductDetail);
        log.info("Added Product Detail: {}",savedProductDetail);

    }
}