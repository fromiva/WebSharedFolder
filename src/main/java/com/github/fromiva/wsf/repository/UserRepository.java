package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** Interface to provide access to {@code User} specific repository. */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Search User by email.
     * @param email email to search
     * @return optional with User if found, empty optional otherwise.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks that User with provided email actually exist.
     * @param email email to search
     * @return {@code true} if user exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
}
