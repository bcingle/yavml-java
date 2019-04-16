package com.github.bcingle.yavml.yavmlapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A filter that extracts username and password from request body, looks up
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        super.setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // get User object from request body
            UserDto user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
            // create an authentication object from user object
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsernameOrEmail(), user.getPassword(), new ArrayList<>());
            // attempt to authenticate with the user's username and password
            return this.getAuthenticationManager().authenticate(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtTokenProvider.createToken(authResult.getName(), new ArrayList<>());
        jwtTokenProvider.addTokenToResponse(token, response);
    }

    @Data
    public static class UserDto {
        private String usernameOrEmail;
        private String password;
    }
}
