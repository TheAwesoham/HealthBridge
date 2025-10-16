package com.app.doctor.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//this filter only runs once per request
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper; //used for token parsing and validation

    @Autowired
    private UserDetailsService userDetailsService; //loads user details 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //extract the authorization header  
        final String requestHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        //check if it starts with "Bearer"
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = jwtHelper.getUsernameFromToken(token); //extract the username 
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired", e);
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT Token", e);
            } catch (Exception e) {
                logger.error("JWT processing error", e);
            }
        } else {
            logger.debug("No Bearer token found in request header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //load user details

            //validate the token using user details
            if (jwtHelper.validateToken(token, userDetails)) {
                //create authentication object to represent the user in spring security
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //attach request details
                SecurityContextHolder.getContext().setAuthentication(authentication); //set authentication in the SecurityContext to tell spring security that the current user is authenticated
                logger.debug("JWT Token validated successfully for user: {}", username);
            } else {
                logger.warn("JWT Token validation failed for user: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
