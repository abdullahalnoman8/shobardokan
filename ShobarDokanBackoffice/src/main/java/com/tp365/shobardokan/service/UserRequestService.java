package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRequestService {

    @Autowired
    UserRequestRepository userRequestRepository;

    public boolean add(UserRequest ur) {
        ur = userRequestRepository.save(ur);
        if(ur.getId()>0){
            return true;
        }
        return false;
    }

    public List<UserRequest> findAllRequestedProductsByUserId(int userId){
        return userRequestRepository.findAllRequestedProductByUserId(userId);
    }

}
