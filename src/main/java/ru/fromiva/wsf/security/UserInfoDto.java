package ru.fromiva.wsf.security;

/**
 * Utility class to transfer information about user's full name.
 * @param firstName user's first name
 * @param middleName user's middle name
 * @param lastName user's last name
 * @param email eser's email address
 */
public record UserInfoDto(String firstName,
                          String middleName,
                          String lastName,
                          String email) { }
