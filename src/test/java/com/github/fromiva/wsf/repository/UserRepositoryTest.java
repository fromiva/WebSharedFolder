package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.model.UserSecurityRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
                true, true, true, true, UserSecurityRole.USER);
        User expected = userRepository.save(user);
        Optional<User> actual = userRepository.findByEmail(expected.getEmail());
        assertThat(Optional.of(expected)).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void findByEmailWhenNotExistsThenEmptyOptional() {
        String email = "user@example.com";
        assertThat(userRepository.findByEmail(email)).isEmpty();
    }

    @Test
    void existsByEmailWhenExistsThenTrue() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        user = userRepository.save(user);
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    void existsByEmailWhenNotExistsThenFalse() {
        String email = "user@example.com";
        assertThat(userRepository.existsByEmail(email)).isFalse();
    }
}
