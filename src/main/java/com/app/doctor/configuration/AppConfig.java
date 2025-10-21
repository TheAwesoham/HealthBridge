package com.app.doctor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration //marks this class as a spring configuration class (used to define beans)
public class AppConfig {
    
    //define a UserDetailsService bean that provides user information for authentication
    @Bean
    public UserDetailsService userDetailsService() {
        //create an in-memory user with username and password
        //the password is encoded using BCrypt for security
        //the user is assigned the role "ADMIN"
        UserDetails userDetails = User.builder().username("soham").password(passwordEncoder().encode("soham")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    //BCrypt is a strong hashing algorithm that makes passwords resistant to brute-force and rainbow table attacks
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager bean is used to authenticate credentials
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
