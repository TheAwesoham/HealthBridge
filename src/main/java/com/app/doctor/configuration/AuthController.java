package com.app.doctor.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.doctor.entity.JwtRequest;
import com.app.doctor.entity.JwtResponse;
import com.app.doctor.security.JwtHelper;

@RestController //the REST controller class is used to handle HTTP requests 
@RequestMapping("/auth") //all endpoints in this controller start with "/auth"
public class AuthController {
    
    @Autowired
    private UserDetailsService userDetailsService; //loads user details for authentication

    @Autowired
    private AuthenticationManager manager; //authentication component used to validate credentials

    @Autowired
    private JwtHelper helper; //helper class responsible for generating and validating JWT tokens

    //used to record authentication events and useful information
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    //accepts user credentials, validates them, and issues a JWT token upon success
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword()); //authenticate email and password from JwtRequest
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail()); //used to load user credentials 
        String token = this.helper.generateToken(userDetails); //generates a JWT token for the authenticated user

        //build a response object containing the token and username
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        logger.info("JWT Token issued for {}", request.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK); //return the JWT token in the HTTP response
    }

    //performs the actual authentication process using spring security
    private void doAuthenticate(String email, String password) {
        //create a username and password authentication token
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password !!"); //throw exception if authentication fails
        }
    }

    //return a simple message on authentication failures
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
