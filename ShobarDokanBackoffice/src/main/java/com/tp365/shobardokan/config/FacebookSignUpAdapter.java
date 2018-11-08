package com.tp365.shobardokan.config;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UsersRoles;
import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.model.enums.UserStatus;
import com.tp365.shobardokan.repository.UserRepository;
import com.tp365.shobardokan.repository.UsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;

import java.util.Date;

@Slf4j
@Configuration
public class FacebookSignUpAdapter implements ConnectionSignUp {
//			private final AtomicLong userIdSequence = new AtomicLong();
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UsersRolesRepository usersRolesRepository;
	
	@Override
	public String execute(Connection<?> connection) {
		User user = new User();
		Facebook facebook = (Facebook) connection.getApi();
		String [] fields = new String [] {"id", "name", "email", "first_name", "last_name"};
		org.springframework.social.facebook.api.User facebookUser = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
		log.info(facebookUser.getName(), facebookUser.getId(), facebookUser.getEmail());
		user.setEmail(facebookUser.getEmail());
		user.setUsername(connection.getKey().toString());
		user.setPassword(null);
		user.setUserStatus(UserStatus.INACTIVE);
		user.setCreatedDate(new Date());
		//				user.setPhone(connection.fetchUserProfile().);
		user = userRepository.add(user);
		usersRolesRepository.add(new UsersRoles(user.getId(), Roles.USER.name()));
		return Integer.toString(user.getId());
	}
}
