package com.tp365.shobardokan.service;


import com.tp365.shobardokan.model.AuthenticatedUser;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import com.tp365.shobardokan.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(userName);
        if(user == null){
            throw new UsernameNotFoundException("UserName : " + userName+ "Not Found");
        }
        Set<GrantedAuthority> authority = Utils.mapRolesToAuthorities(usersRolesRepository.resolveRolesForUser(user));
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user.getUsername(),
                user.getPassword(), authority).addId(user.getId());
//	    MDC.put("userId", String.valueOf(user.getId()));
        return authenticatedUser;
    }
}