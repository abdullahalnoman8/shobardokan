package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRequest;
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
        ProductDetails productDetails = new ProductDetails();
        productDetails.setCategory(category);
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1);
        productDetails.setUserRequest(userRequest);
        productDetails.setUrl("https:www.abc.com");
        productDetails.setProductDescription("Test product Description");
        productDetails.setUnitPrice(89.63);
        productDetails.setDeliveryFee(15.89);
        productDetails.setStatus(ProductDetails.Status.ACCEPTED);
        productDetails.setCostPrice(789.63);
        productDetails.setComments("Test Comment");
        User user = new User();
        user.setId(3);
        productDetails.setUser(user);

        ProductDetails savedProductDetails = productDetailRepository.add(productDetails);
        Assert.assertNotNull(savedProductDetails);
        log.info("Added Product Detail: {}",savedProductDetails);

    }
}