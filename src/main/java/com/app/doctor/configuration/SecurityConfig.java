package com.app.doctor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.doctor.security.JwtAuthenticationEntryPoint;
import com.app.doctor.security.JwtAuthenticationFilter;

@Configuration //marks this class as a spring configuration class (used to define beans)
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationEntryPoint point; //handles unauthorized access attempts (like invalid or missing JWT)
    @Autowired
    private JwtAuthenticationFilter filter; //custom filter that validates JWT tokens before processing requests

    @Bean //the main security configuration for the application
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) //disable CSRF protection (not needed for stateless JWT-based authentication)
                .cors(cors -> cors.disable())
                //define authorization rules for incoming HTTP requests
                .authorizeHttpRequests(auth -> 
                        auth.requestMatchers("/auth/login").permitAll() //allow public access to the login endpoint as it is used to generate JWT tokens
                        .anyRequest().authenticated()) //require authentication for all other endpoints

                .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) //custom handler for unauthorized requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); //register the custom JWT filter before the default authentication filter
        return http.build(); //build and return the configured SecurityFilterChain
    }
}
