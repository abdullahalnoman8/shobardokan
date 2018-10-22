package com.tp365.shobardokan.repository;

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
public class TestRoleRepository {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findRoleNameById(){
        Integer roleId= 3;
        String roleName = roleRepository.findRoleNameById(roleId);
        Assert.assertNotNull(roleName);
        log.info("Role Name: {}",roleName);
    }

}