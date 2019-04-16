package com.github.bcingle.yavml.yavmlapi.service;

import com.github.bcingle.yavml.yavmlapi.model.User;
import com.github.bcingle.yavml.yavmlapi.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    public void testFindByEmail_UserFound() {
        User user = new User();
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.findByEmail("email@me.com")).thenReturn(Optional.of(user));
        UserService userService = new UserService(mockRepo);
        User found = userService.findUserByUsernameOrEmail("email@me.com");

        assertThat(found).isNotNull().isEqualTo(user);
        verify(mockRepo).findByEmail("email@me.com");
    }

    public void testFindByUsername_UserFound() {
        User user = new User();
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.findByUsername("username")).thenReturn(Optional.of(user));
        UserService userService = new UserService(mockRepo);
        User found = userService.findUserByUsernameOrEmail("username");

        assertThat(found).isNotNull().isEqualTo(user);
        verify(mockRepo).findByUsername("username");
    }

}
