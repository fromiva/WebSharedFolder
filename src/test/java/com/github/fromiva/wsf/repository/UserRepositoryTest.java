package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    /** {@code User}-specific Spring Data JPA repository. */
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clearRepositoryRecords() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmailWhenExistsThenGetTheSame() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true);
        User expected = userRepository.save(user);
        Optional<User> actual = userRepository.findByEmail(expected.getEmail());
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void findByEmailWhenNotExistsThenEmptyOptional() {
        String email = "user@example.com";
        Optional<User> expected = Optional.empty();
        Optional<User> actual = userRepository.findByEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    void existsByEmailWhenExistsThenTrue() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true);
        user = userRepository.save(user);
        boolean expected = true;
        boolean actual = userRepository.existsByEmail(user.getEmail());
        assertEquals(expected, actual);
    }

    @Test
    void existsByEmailWhenNotExistsThenFalse() {
        String email = "user@example.com";
        boolean expected = false;
        boolean actual = userRepository.existsByEmail(email);
        assertEquals(expected, actual);
    }
}
