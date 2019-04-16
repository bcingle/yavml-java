package com.github.bcingle.yavml.yavmlapi.security;

import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import com.github.bcingle.yavml.yavmlapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        YavmlUser user = userService.findUserByUsernameOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return this.convertUserToUserDetails(user);
    }

    public UserDetails convertUserToUserDetails(YavmlUser user) {
        String[] authorities;
        if ("admin".equals(user.getUsername())) {
            authorities = new String[] {"ROLE_USER", "ROLE_ADMIN"};
        } else {
            authorities = new String[] {"ROLE_USER"};
        }
        return User.withUsername(user.getUsername())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .password(user.getPassword())
                .build();
    }
}
