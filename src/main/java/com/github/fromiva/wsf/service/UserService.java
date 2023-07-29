package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/** Interface to handle {@code User} specific business logic. */
public interface UserService extends UserDetailsService {

    /**
     * Returns number of the entities in the repository.
     * @return number of the entities
     */
    long count();

    /**
     * Checks that the {@code User} with provided ID exists in the repository.
     * @param id ID of the {@code User} to check
     * @return {@code true} if the {@code User} exists, {@code false} otherwise
     */
    boolean existsById(Long id);

    /**
     * Checks that the {@code User} with provided username exists in the repository.
     * @param username username of the {@code User} to check
     * @return {@code true} if the {@code User} exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Finds for the {@code User} with provided ID in the repository.
     * @param id ID of the {@code User} to search
     * @return {@code Optional} with search result or empty {@code Optional} if nothing found
     */
    Optional<User> findById(Long id);

    /**
     * Finds for the {@code User} with provided username in the repository.
     * @param username of the {@code User} to search
     * @return {@code Optional} with the search result or empty {@code Optional} if nothing found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds for all the users in the repository.
     * @return {@code List} with the search result or empty {@code List} if nothing found
     */
    List<User> findAll();

    /**
     * Saves a {@code User} in the repository.
     * @param user entity to save
     * @return original {@code user} object with the actual ID
     */
    Optional<User> save(User user);

    /**
     * Updates a {@code User} in the repository.
     * @param user entity to update
     * @return {@code true} if success or {@code false} otherwise
     */
    boolean update(User user);

    /**
     * Removes a {@code User} with provided ID from the repository.
     * @param id ID of the {@code User} to remove
     */
    void deleteById(Long id);

    /**
     * Deletes all the entities from the repository.
     */
    void deleteAll();
}
