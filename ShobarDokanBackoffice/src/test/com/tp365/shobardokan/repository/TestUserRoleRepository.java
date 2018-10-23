package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRole;
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
public class TestUserRoleRepository {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void testAdd(){
        UserRole userRole = new UserRole();

        userRole.setRoleId(3);
        User user = new User();
        user.setId(3);
        userRole.setUser(user);

        UserRole savedUserRole = userRoleRepository.add(userRole);
        Assert.assertNotNull(savedUserRole.getId());
        log.info("User  Role Info: {}",savedUserRole);
    }
}