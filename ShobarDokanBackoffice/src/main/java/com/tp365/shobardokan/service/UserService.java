package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserRole;
import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.repository.RoleRepository;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;


    public boolean isRegistered(User user) {
        try{
            if(!user.getPassword().equals(user.getPassword_confirmation())){
                return false;
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setUserStatus(User.UserStatus.INACTIVE);
            User savedUser = userRepository.add(user);
            log.info("New User Added With Information: {}",savedUser.toString());
            if(savedUser.getId() !=null){
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                Role role = roleRepository.findRoleNameByname(Roles.USER.name());
                if(role.getId() != null){
                    savedUser.setRole(role);
                    userRole.setRoleId(role.getId());
                    UserRole savedUserRole = userRoleRepository.add(userRole);
                    return savedUserRole.getId() != null;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }catch (Exception ex){
            log.error("Error Found In Registration:{}",ex.getLocalizedMessage());
            return false;
        }

    }
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}