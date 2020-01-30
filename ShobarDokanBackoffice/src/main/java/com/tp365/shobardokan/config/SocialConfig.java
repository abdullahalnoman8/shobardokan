package com.tp365.shobardokan.config;

import com.tp365.shobardokan.model.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class SocialConfig implements SocialConfigurer {
	
	@Autowired
	DataSource dataSource;
	@Autowired
	FacebookSignUpAdapter facebookSignUpAdapter;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//		connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("facebook.clientId"), environment.getProperty("facebook.clientSecret")));
	}
	
	@Override
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {
			@Override
			public String getUserId() {
//				return SecurityContext.getCurrentUser().getId();
				return String.valueOf(((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getDetails()).getId());
//				return "user-id";
			}
		};
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//		InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
		repository.setConnectionSignUp(facebookSignUpAdapter);
		return repository;
	}
}
