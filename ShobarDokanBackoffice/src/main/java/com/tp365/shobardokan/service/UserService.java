package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.AuthenticatedUser;
import com.tp365.shobardokan.model.Role;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UsersRoles;
import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.model.enums.UserStatus;
import com.tp365.shobardokan.model.phoneverification.AuthorizedResponse;
import com.tp365.shobardokan.repository.RoleRepository;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        try{
            if(userRepository.checkUserNameExists(user.getUsername())) {
                return Boolean.FALSE;
            }else if(userRepository.checkUserEmailExists(user.getEmail())){
                return Boolean.FALSE;
            }else if(userRepository.checkPhoneNumberExists(user.getPhone())){
                return Boolean.FALSE;
            }else{
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user.setUserStatus(UserStatus.INACTIVE);
                user.setIsActive(Boolean.FALSE);
                User savedUser = userRepository.add(user);
                log.info("New User Added With Information: {}",savedUser.toString());
                if(savedUser.getId() !=null){
                    UsersRoles usersRoles = new UsersRoles();
                    usersRoles.setUser(user);
                    Role role = roleRepository.findByName(Roles.USER.name());
                    if(role.getId() != null){
                        savedUser.setRoles(role.getRole());
                        usersRoles.setRole(role);
                        usersRoles.setUser(savedUser);
                        usersRoles.setCreatedAt(new Date());
                        UsersRoles savedUsersRoles = usersRolesRepository.add(usersRoles);
                        return savedUsersRoles.getId() != null;
                    }else {
                        return Boolean.FALSE;
                    }
                }else {
                    return Boolean.FALSE;
                }
            }
        }catch (Exception ex){
            log.error("Error Found In Registration:{}",ex.getLocalizedMessage());
            return Boolean.FALSE;
        }
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public boolean updateUserWithValidPhone(AuthorizedResponse authorizedResponse){
        User user = new User();
        user.setPhone(authorizedResponse.getPhone().getNumber());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = userRepository.findUserByUserName(auth.getName()).getId();
        user.setId(userId);
        return userRepository.updateUser(user);
    }
	
	public User getCurrentUser() {
    	AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	return userRepository.findById(user.getId());
	}
}