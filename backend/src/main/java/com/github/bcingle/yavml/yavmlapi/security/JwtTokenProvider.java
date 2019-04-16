package com.github.bcingle.yavml.yavmlapi.security;

import com.github.bcingle.yavml.yavmlapi.configuration.JwtConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final JwtConfigProperties jwtConfigProperties;

    private final UserDetailsService userDetailsService;

    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtConfigProperties.getSecretKey().getBytes());
    }

    @Autowired
    public JwtTokenProvider(JwtConfigProperties jwtConfigProperties, UserDetailsService userDetailsService) {
        this.jwtConfigProperties = jwtConfigProperties;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtConfigProperties.getTokenDuration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    /**
     * Create an Authentication object from a token
     * @param token
     * @return the authentication object
     */
    public Authentication getAuthenticationFromToken(String token) {
        // get the username from the token
        String username = this.getUsernameFromToken(token);
        // lookup the user by username (throws UsernameNotFoundException)
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        // convert the user into an authentication token
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    /**
     * Extract the username from a JWT token
     * @param token
     * @return the username stored in the token
     */
    public String getUsernameFromToken(String token) {
        // extract the username from the token
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extract the token from request header.  Typically the header is called "Authorization"
     * and the value begins with "Bearer " followed by the token value, but these properties
     * can be configured at runtime.
     * @param req the http request
     * @return the token if present in the request, or null if no token provided
     */
    public String getTokenFromRequest(HttpServletRequest req) {
        String authToken = req.getHeader(jwtConfigProperties.getTokenHeaderKey());
        if (authToken != null && authToken.startsWith(jwtConfigProperties.getTokenHeaderValuePrefix())) {
            return authToken.substring(jwtConfigProperties.getTokenHeaderValuePrefix().length());
        }
        return null;
    }

    public void addTokenToResponse(String token, HttpServletResponse res) {
        String header = jwtConfigProperties.getTokenHeaderKey();
        String value = jwtConfigProperties.getTokenHeaderValuePrefix() + token;
        res.addHeader(header, value);
    }

    /**
     * Validate that a token is still valid
     * @param token
     * @return true if token is valid (has not expired), and false otherwise
     */
    public boolean validateToken(String token) {
        // parse claims from token
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.signingKey)
                .parseClaimsJws(token);
        // check if expiration has passed
        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

}
