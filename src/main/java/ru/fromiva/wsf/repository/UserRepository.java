package ru.fromiva.wsf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.fromiva.wsf.model.User;
import ru.fromiva.wsf.model.UserSecurityRole;

import java.util.List;
import java.util.Optional;

/** Interface to provide access to {@code User} specific repository. */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Search User by email.
     * @param email email to search
     * @return optional with User if found, empty optional otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks that User with provided email actually exist.
     * @param email email to check
     * @return {@code true} if User exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find all the active users (that enabled, not blocked and not expired).
     * @return list of all the active users or empty list if nothing found
     */
    @Transactional(readOnly = true)
    @Query("select u from User as u where"
            + " u.accountNonExpired = true"
            + " and u.accountNonLocked = true"
            + " and u.credentialsNonExpired = true"
            + " and u.enabled = true")
    List<User> findAllActive();

    /**
     * Find all the users with specified security role.
     * @param role to filter
     * @return list of all the users with specified security role or empty list if nothing found
     */
    List<User> findAllByUserSecurityRole(UserSecurityRole role);

    /**
     * Updates the full name of the user with provided ID.
     * @param id of a desired user
     * @param firstName new first name
     * @param middleName new middle name
     * @param lastName new last name
     * @return number of updated entities
     */
    @Transactional
    @Modifying
    @Query("update User u set"
            + " u.firstName = :firstName,"
            + " u.middleName = :middleName,"
            + " u.lastName = :lastName"
            + " where u.id = :id")
    int updateFullName(@Param("id") Long id,
                       @Param("firstName") String firstName,
                       @Param("middleName") String middleName,
                       @Param("lastName") String lastName);

    /**
     * Updates the email of the user with provided ID.
     * @param id of a desired user
     * @param email new email
     * @return number of updated entities
     */
    @Transactional
    @Modifying
    @Query("update User u set u.email = :email where u.id = :id")
    int updateEmail(@Param("id") Long id,
                    @Param("email") String email);

    /**
     * Updates the password of the user with provided ID.
     * @param id of a desired user
     * @param password new password encoded by password encoder
     * @return number of updated entities
     */
    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    int updatePassword(@Param("id") Long id,
                       @Param("password") String password);
}
