package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.RoleRepository;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Autowired
    private RoleRepository roleRepository;


    public boolean isRegistered(User user) {
        // todo should be updated to proper logic
//        try{
//            if(!user.getPassword().equals(user.getPassword_confirmation())){
//                return false;
//            }
//            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//            user.setUserStatus(User.UserStatus.INACTIVE);
//            User savedUser = userRepository.add(user);
//            log.info("New User Added With Information: {}",savedUser.toString());
//            if(savedUser.getId() !=null){
//                UsersRoles usersRoles = new UsersRoles();
//                usersRoles.setUser(user);
//                Role role = roleRepository.findByName(Roles.USER.name());
//                if(role.getId() != null){
//                    savedUser.setRole(role);
//                    usersRoles.setRoleId(role.getId());
//                    UsersRoles savedUsersRoles = usersRolesRepository.add(usersRoles);
//                    return savedUsersRoles.getId() != null;
//                }else {
//                    return false;
//                }
//            }else {
//                return false;
//            }
//        }catch (Exception ex){
//            log.error("Error Found In Registration:{}",ex.getLocalizedMessage());
//            return false;
//        }
        return false;

    }
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}