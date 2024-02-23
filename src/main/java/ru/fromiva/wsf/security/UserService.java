package ru.fromiva.wsf.security;

import ru.fromiva.wsf.dto.UserInfoDto;

import java.util.List;
import java.util.Optional;

/** Interface to handle {@code User} specific business logic. */
public interface UserService {

    /**
     * Returns number of all the entities in the repository.
     * @return number of all the entities
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
     * Finds for all the users in the repository that enabled, not blocked and not expired.
     * @return {@code List} with the search result or empty {@code List} if nothing found
     */
    List<User> findAllActive();

    /**
     * Find all the users with specified security role.
     * @param role to filter
     * @return list of all the users with specified security role or empty list if nothing found
     */
    List<User> findAllByUserSecurityRole(UserSecurityRole role);

    /**
     * Saves a {@code User} in the repository.
     * @param user entity to save
     * @return original {@code user} object with the actual ID
     */
    User save(User user);

    /**
     * Updates a {@code User} in the repository.
     * @param user entity to update
     * @return the updated object
     */
    User update(User user);

    /**
     * Updates a {@code User}'s first name, middle name and last name in the repository.
     * @param id user id
     * @param dto data transfer object with information to update
     * @return {@code true} if success or {@code false} otherwise
     */
    boolean updateFullName(Long id, UserInfoDto dto);

    /**
     * Updates a {@code User}'s email in the repository.
     * @param id user id
     * @param email new email to update
     * @return {@code true} if success or {@code false} otherwise
     */
    boolean updateEmail(Long id, String email);

    /**
     * Updates a {@code User}'s email in the repository.
     * @param id user id
     * @param password new password to update
     * @return {@code true} if success or {@code false} otherwise
     */
    boolean updatePassword(Long id, String password);

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
