package com.github.bcingle.yavml.yavmlapi.security;

import com.github.bcingle.yavml.yavmlapi.configuration.JwtConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    public static final String REGISTER_URL = "/register";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // enable CORS filter
                .cors().and()
                // disable CSRF filter
                .csrf().disable()
                // enable request authorization
                .authorizeRequests()
                    // enable non-authenticated access to the registration and login endpoints
                    .antMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
                    // all other requests require authentication/authorization
                    .anyRequest().authenticated().and()
                // add filter to check username and password and create JWT token
                .addFilter(jwtAuthenticationFilterBean())
                // add filter to check JWT token
                .addFilter(jwtAuthorizationFilterBean())
                // disable session management, because JWT carries all necessary state
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    /**
     * Expose the AuthenticationManager configured above as a bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilterBean() throws Exception {
        return new JwtAuthorizationFilter(this.authenticationManagerBean(), jwtTokenProvider);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean() throws Exception {
        return new JwtAuthenticationFilter(this.authenticationManagerBean(), jwtTokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authEntryPoint() {
        return (req, res, exc) -> {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
