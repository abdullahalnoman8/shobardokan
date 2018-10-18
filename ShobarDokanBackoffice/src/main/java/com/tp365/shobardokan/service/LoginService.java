package com.tp365.shobardokan.service;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.RoleRepository;
import com.tp365.shobardokan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LoginService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findUserByUserName(userName);
        if(user ==null){
            throw new UsernameNotFoundException("UserName : " + userName+ "Not Found");
        }
        final String roleName = roleRepository.findRoleNameById(user.getRole().getId());
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}