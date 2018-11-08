package com.tp365.shobardokan.config;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import com.tp365.shobardokan.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.NativeWebRequest;

@Slf4j
@Configuration
public class FacebookSignInAdapter implements SignInAdapter {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UsersRolesRepository usersRolesRepository;
	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		User user = userRepository.findUserByUserName(connection.getKey().toString());
		Facebook facebook = (Facebook) connection.getApi();
		if (user == null) {
			log.error("No user found corresponding to the user: {}", connection.getKey().toString());
//			log.error("User Information: {}", facebook.userOperations().getUserProfile());
		}
		facebook.userOperations().getUserPermissions().stream().forEach(x -> log.info("Permission {}: {}", x.getName(), x.getStatus()));
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, Utils.mapRolesToAuthorities(usersRolesRepository.resolveRolesForUser(user))));
		return null;
	}
}
