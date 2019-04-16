package com.github.bcingle.yavml.yavmlapi.controller;

import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import com.github.bcingle.yavml.yavmlapi.security.AuthConfig;
import com.github.bcingle.yavml.yavmlapi.service.UserService;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Objects;

@RestController
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(AuthConfig.REGISTER_URL)
    public void register(@RequestBody @Valid RegistrationForm registrationForm) throws EmailTakenException, UsernameTakenException {
        registrationForm.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        YavmlUser user = registrationForm.toYavmlUser();

        userService.saveUnique(user);
    }

    @Data
    public static class RegistrationForm {

        @NonNull
        private String username;

        @NonNull
        private String email;

        @NonNull
        @Size(min = 8)
        private String password;

        public RegistrationForm() {}

        public YavmlUser toYavmlUser() {
            YavmlUser user = new YavmlUser();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }
    }

}
