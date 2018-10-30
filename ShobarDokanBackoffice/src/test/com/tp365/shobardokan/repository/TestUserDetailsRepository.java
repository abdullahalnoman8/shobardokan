package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserDetailsRepository {

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public  void testAdd(){
        UserDetails userDetails = new UserDetails();
        User user = new User();
        user.setId(3);
        userDetails.setUser(user);
        userDetails.setFirst_name("Abdullah");
        userDetails.setLast_name("Al Noman");
        userDetails.setGender(UserDetails.Gender.MALE);
        userDetails.setDate_of_birth(new Date());
        userDetails.setPresent_address("Test Present Address");
        userDetails.setMailing_address("Test Mailing Address");
        userDetails.setEmergency_contact_number("000000000");

        UserDetails savedUserDetails = userDetailsRepository.add(userDetails);
        Assert.assertNotNull(savedUserDetails.getId());
        log.info("User Details With ID: {}",savedUserDetails.getId());
        Assert.assertNotNull(savedUserDetails);
        log.info("User Details Added With Value: {}",savedUserDetails.toString());

    }
}