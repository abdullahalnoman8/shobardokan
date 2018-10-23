package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserAddress;
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
public class TestUserAddressRepository {
    @Autowired
    private UserAddressRepository userAddressRepository;

    @Test
    public void testAdd(){

        UserAddress userAddress = new UserAddress();
        userAddress.setCity("Dhaka");
        userAddress.setState("Dhaka");
        userAddress.setStreetAddressOne("Address One");
        userAddress.setStreetAddressTwo("Address Two");
        userAddress.setZipCode("1207");
        User user = new User();
        user.setId(3);
        userAddress.setUser(user);


        UserAddress savedUserAddress = userAddressRepository.add(userAddress);
        Assert.assertNotNull(savedUserAddress.getId());
        log.info("User Address: {}",savedUserAddress);
    }
}