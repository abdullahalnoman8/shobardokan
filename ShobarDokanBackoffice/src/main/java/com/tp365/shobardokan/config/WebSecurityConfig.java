package com.tp365.shobardokan.config;


import com.tp365.shobardokan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.tp365.shobardokan"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private LoginService loginService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/register","/forgetPassword","/signin/**","/signup/**").permitAll()
                .antMatchers("/", "/requestProduct/**").hasAnyAuthority("ADMIN","USER","TRAVELER")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/dashboard")
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login?logout")
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(loginService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/*","/register/*", "/static/**", "/vendor/**", "/dist/**", "/css/**",  "/fonts/**","/images/**", "/js/**");
    }
    
    
    // Facebook Login
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
	
	@Bean
	@Inject
	public ProviderSignInController providerSignInController (ConnectionFactoryLocator connectionFactoryLocator, FacebookSignInAdapter facebookSignInAdapter) {
//		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, facebookSignInAdapter);
	}
}