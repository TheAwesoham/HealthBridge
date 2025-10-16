package com.app.doctor.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    //this method is called when an unauthorized user tries to access a secured endpoint without a valid JWT token
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //set the HTTP response status to 401 (unauthorized)
        PrintWriter writer = response.getWriter(); //the response writer sends a message back to the client
        writer.println("Access Denied !! " + authException.getMessage());
    }
}
