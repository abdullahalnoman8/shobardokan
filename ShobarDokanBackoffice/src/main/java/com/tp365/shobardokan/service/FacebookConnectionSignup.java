package com.tp365.shobardokan.service;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FacebookConnectionSignup implements ConnectionSignUp{


    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        log.info("Sign Up Facebook Connection: {}",connection.toString());
        User user = new User();
        user.setUsername(connection.getDisplayName());
        log.info("Connection Information API: {}, URL: {}",connection.getApi(),connection.getProfileUrl());
        user.setPassword(RandomStringUtils.randomAlphanumeric(8));
        // Todo if already an user exist we will declined the saving process
        user = userRepository.findUserByUserName(connection.getDisplayName());
        if(user.getUsername() == null){
            userRepository.add(user);
        }
        return user.getUsername();
    }
}