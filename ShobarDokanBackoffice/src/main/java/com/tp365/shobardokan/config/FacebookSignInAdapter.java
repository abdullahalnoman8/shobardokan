package com.tp365.shobardokan.config;

import com.tp365.shobardokan.model.AuthenticatedUser;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import com.tp365.shobardokan.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Permission;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Set;
import java.util.stream.Collectors;

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
		
		// todo reshape the setting of authentication below with AuthenticatedUser
		// add authentication
		Set<GrantedAuthority> authorities = Utils.mapRolesToAuthorities(usersRolesRepository.resolveRolesForUser(user));
		Authentication authentication = new UsernamePasswordAuthenticationToken(new AuthenticatedUser(user.getUsername(),
				""/*Password is left vacant, since social logins don't have any*/,
				authorities), null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if (user.getEmail() == null) {
			// redirect to input email
			log.info("User has not provided email yet");
			Set<Permission> emailPermission = facebook.userOperations().getUserPermissions().stream().filter(
					permission ->  permission.getName().equals("email")
			).collect(Collectors.toSet());
			
			// should execute once. Update later to a more optimal Java 8 format.
			for (Permission p: emailPermission) {
				if (p.isGranted()) {
					org.springframework.social.facebook.api.User facebookUser = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, "email");
					user.setEmail(facebookUser.getEmail());
					log.info("User has provided email permission, fetched email: {}", facebookUser.getEmail());
					userRepository.update(user);
				} else {
					log.info("Email permission not provided, redirecting to provide email page");
					return "/confirmEmail";
				}
			}
		}
		return null;
	}
}
