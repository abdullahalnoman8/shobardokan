package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    /** ---------------------------- Adding New User ----------------------------**/
    @Test
    public void userAdd(){
        User user = new User();
        user.setUsername("tuser");
        user.setEmail("testemail@gmail.com");
        user.setPassword(new BCryptPasswordEncoder().encode("aaaaa@"));

        User savedUser = userRepository.add(user);
        Assert.assertNotNull(savedUser.getId());
        log.info("New User Added With Information: {}",savedUser.toString());
        if(savedUser.getId() != null){
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            Role role = roleRepository.findRoleNameByname("User");
            Assert.assertNotNull(role.getId());
            savedUser.setRole(role);
            userRole.setRoleId(user.getRole().getId());
            UserRole savedUserRole = userRoleRepository.add(userRole);
            Assert.assertNotNull(savedUserRole.getId());
            log.info("User Role Added With Information: {}",savedUserRole.toString());
        }
    }

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