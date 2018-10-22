package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll(){
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList = (ArrayList<User>) userRepository.findAll();
        log.info("User List : {}",userArrayList.toString());
        Assert.assertNotNull(userArrayList);
    }

    @Test
    public void findUserByUserName(){
       String username = "Gazi Opu";
       User user = userRepository.findUserByUserName(username);
       Assert.assertNotNull(user.getId());
       log.info("User Data: {}",user.toString());
       Assert.assertEquals(username,user.getUsername());
    }

}