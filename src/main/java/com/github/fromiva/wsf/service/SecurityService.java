package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.UserNameDto;
import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.util.IncorrectPasswordException;
import org.springframework.security.core.userdetails.UserDetailsService;

/** Interface to handle security-specific business logic. */
public interface SecurityService extends UserDetailsService {

    /**
     * Retrieves and returns principal (logged-in user) for the current HTTP session.
     * @return principal for the current session
     */
    User getPrincipal();

    /**
     * Retrieves and returns principal (logged-in user) ID for the current HTTP session.
     * @return principal ID for the current session
     */
    Long getPrincipalId();

    /**
     * Retrieves and returns principal (logged-in user) full name for the current HTTP session.
     * @return data transfer object with principal full name for the current session
     */
    UserNameDto getPrincipalName();

    /**
     * Retrieves the current principal entity from persistent storage and updates the principal
     * object in the Spring Security context by actual one.
     */
    void refreshPrincipal();

    /**
     * Updates a {@code User}'s full name in the repository.
     * @param dto data transfer object with principal full name
     * @return {@code true} if success or {@code false} otherwise
     */
    boolean changePrincipalFullName(UserNameDto dto);

    /**
     * Updates a {@code User}'s email in the repository.
     * @param email new email to update
     * @param password user password to confirm the operation
     * @return {@code true} if success or {@code false} otherwise
     * @throws IncorrectPasswordException when user provides incorrect {@code password}
     * to perform the operation
     */
    boolean changePrincipalEmail(String email, String password) throws IncorrectPasswordException;

    /**
     * Updates a {@code User}'s email in the repository.
     * @param newPassword new password to update
     * @param oldPassword user old password to confirm the operation
     * @return {@code true} if success or {@code false} otherwise
     * @throws IncorrectPasswordException when user provides incorrect  {@code oldPassword}
     * to perform the operation
     */
    boolean changePrincipalPassword(String newPassword, String oldPassword)
            throws IncorrectPasswordException;
}
