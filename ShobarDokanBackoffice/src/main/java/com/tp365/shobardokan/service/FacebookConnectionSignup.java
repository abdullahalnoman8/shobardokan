package com.tp365.shobardokan.service;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp{


    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        System.out.println("signup === Facebook Connection");
        final User user = new User();
        user.setUsername(connection.getDisplayName());
        System.out.println("Profile URL: "+connection.getProfileUrl());
        user.setPassword(RandomStringUtils.randomAlphanumeric(8));
        userRepository.save(user);
        return user.getUsername();
    }
}