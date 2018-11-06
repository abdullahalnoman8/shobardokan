package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserRequestService {

    @Autowired
    UserRequestRepository userRequestRepository;
    @Autowired
    UserService userService;

    public boolean add(UserRequest userRequest) {
        User user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        userRequest.setUser(user);
        userRequest.setStatus(UserRequest.Status.OPEN);
        userRequest.setCreatedAt(new Date());
        userRequest.setUpdatedAt(new Date());
        return userRequestRepository.save(userRequest).getId() != null;
    }

    public List<UserRequest> findAllRequestedProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = userService.findUserByUserName(auth.getName()).getId();
        List<UserRequest> userRequestProductList;
        if(auth.getAuthorities().toString().contains(String.valueOf(Roles.USER))){
            userRequestProductList = userRequestRepository.findAllRequestedProductByUserId(userId);
        } else {
            userRequestProductList = userRequestRepository.findAll();
        }
        return userRequestProductList;
    }

}
