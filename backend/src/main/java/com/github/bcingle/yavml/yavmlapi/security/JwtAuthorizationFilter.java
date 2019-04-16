package com.github.bcingle.yavml.yavmlapi.security;

import com.github.bcingle.yavml.yavmlapi.configuration.JwtConfigProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An Authorization filter
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // get the JWT token from request, if provided
        String jwtToken = jwtTokenProvider.getTokenFromRequest(req);

        // if token is available and valid (not expired), set the authentication on the current security context
        if (jwtToken != null) {
            Authentication authToken = jwtTokenProvider.getAuthenticationFromToken(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // activate the next filter in the chain
        chain.doFilter(req, res);
    }
}
