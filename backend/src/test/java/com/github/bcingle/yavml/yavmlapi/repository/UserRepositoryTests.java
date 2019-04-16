package com.github.bcingle.yavml.yavmlapi.repository;

import com.github.bcingle.yavml.yavmlapi.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("myEmail");
        user.setUsername("myUsername");

        entityManager.persistAndFlush(user);

        user = new User();
        user.setEmail("anotherEmail");
        user.setUsername("anotherUsername");

        entityManager.persistAndFlush(user);

        Optional<User> found = userRepo.findByEmail("myEmail");

        assertThat(found).isNotNull();
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotNull().isGreaterThan(0);
        assertThat(found.get().getEmail()).isEqualTo("myEmail");
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setEmail("myEmail");
        user.setUsername("myUsername");

        entityManager.persistAndFlush(user);

        user = new User();
        user.setEmail("anotherEmail");
        user.setUsername("anotherUsername");

        entityManager.persistAndFlush(user);

        Optional<User> found = userRepo.findByUsername("myUsername");

        assertThat(found).isNotNull();
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotNull().isGreaterThan(1);
        assertThat(found.get().getUsername()).isEqualTo("myUsername");
    }
}
