package ru.fromiva.wsf.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest
@Import(BCryptPasswordEncoder.class)
class UserRepositoryTest {

    /** {@code User}-specific Spring Data JPA repository. */
    @Autowired
    private UserRepository userRepository;

    /** Password encoder to support password encryption / decryption Spring Security mechanism. */
    @Autowired
    private PasswordEncoder encoder;

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

    @Test
    void whenPersistWithNullMiddleNameThenNoException() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        assertThatNoException().isThrownBy(() -> userRepository.save(user));
    }

    @Test
    void updateFullNameWhenExistsThenUpdateWithoutMiddleName() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        user = userRepository.save(user);
        user.setFirstName("Firstname");
        user.setLastName("Lastname");
        assertThat(userRepository.updateFullName(user.getId(),
                user.getFirstName(), user.getMiddleName(), user.getLastName()))
                .isNotZero();
        assertThat(userRepository.findById(user.getId()))
                .usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void updateFullNameWhenExistsThenUpdateWithMiddleName() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        user = userRepository.save(user);
        user.setFirstName("First");
        user.setMiddleName("Middle");
        user.setLastName("Last");
        assertThat(userRepository.updateFullName(user.getId(),
                user.getFirstName(), user.getMiddleName(), user.getLastName()))
                .isNotZero();
        assertThat(userRepository.findById(user.getId()))
                .usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void updateFullNameWhenNotExistsThenNothingUpdated() {
        assertThat(userRepository.updateFullName(1L, "Firstname", null, "Lastname")).isZero();
    }

    @Test
    void updateEmailWhenExistsThenUpdate() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        user = userRepository.save(user);
        user.setEmail("new@example.com");
        assertThat(userRepository.updateEmail(user.getId(), user.getEmail())).isNotZero();
        assertThat(userRepository.findById(user.getId()))
                .usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void updateEmailWhenNotExistsThenNothingUpdated() {
        assertThat(userRepository.updateEmail(1L, "new@example.com")).isZero();
    }

    @Test
    void updatePasswordWhenExistsThenUpdate() {
        User user = new User(
                0L, "user@example.com", "{noop}password",
                "Stub", null, "User",
                true, true, true, true, UserSecurityRole.USER);
        user = userRepository.save(user);
        user.setPassword(encoder.encode("new password"));
        assertThat(userRepository.updatePassword(user.getId(), user.getPassword())).isNotZero();
        assertThat(userRepository.findById(user.getId()))
                .usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void updatePasswordWhenNotExistsThenNothingUpdated() {
        assertThat(userRepository.updatePassword(1L, encoder.encode("new password"))).isZero();
    }
}
