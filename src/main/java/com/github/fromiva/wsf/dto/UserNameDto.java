package com.github.fromiva.wsf.dto;

/**
 * Utility class to transfer information about user's full name.
 * @param firstName user's first name
 * @param middleName user's middle name
 * @param lastName user's last name
 */
public record UserNameDto(String firstName, String middleName, String lastName) { }
