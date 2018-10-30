package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}