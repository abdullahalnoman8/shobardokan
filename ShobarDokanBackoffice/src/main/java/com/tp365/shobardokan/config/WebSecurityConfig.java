package com.tp365.shobardokan.config;


import com.tp365.shobardokan.service.FacebookConnectionSignup;
import com.tp365.shobardokan.service.FacebookSignInAdapter;
import com.tp365.shobardokan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private LoginService loginService;


    private TextEncryptor textEncryptor;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /*Authentic System for facebook login*/
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/register","/forgetPassword","/signin/**","/signup/**").permitAll()
                .antMatchers("/").hasAnyAuthority("Admin","User","Traveler","FACEBOOK_USER")
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



//    /**
//     * Singleton data access object providing access to connections across all users.
//     */
//    @Bean
//    public UsersConnectionRepository usersConnectionRepository() {
//        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(, connectionFactoryLocator, Encryptors.noOpText());
//        repository.setConnectionSignUp(facebookConnectionSignup);
//        return repository;
//    }

    @Bean
    public ProviderSignInController providerSignInController(){
//        ((JdbcUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
//        ((JdbcUsersConnectionRepository) usersConnectionRepository)
//                .setConnectionSignUp(facebookConnectionSignup);
        ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator,usersConnectionRepository,new FacebookSignInAdapter());
        providerSignInController.setSignInUrl("/sign-in-url");
        providerSignInController.setSignUpUrl("/sign-up-url");
        providerSignInController.setPostSignInUrl("/");
        return providerSignInController;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/*","/register/*", "/static/**", "/vendor/**", "/css/**",  "/fonts/**","/images/**", "/js/**");
    }
}