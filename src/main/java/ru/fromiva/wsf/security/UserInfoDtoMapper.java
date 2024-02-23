package ru.fromiva.wsf.security;

import org.springframework.stereotype.Component;

/** Mapper to support {@code UserInfoDto} data transfer object. */
@Component
public class UserInfoDtoMapper {

    /**
     * Produces {@code UserInfoDto} item.
     * @param user source data object
     * @return {@code UserInfoDto} item
     */
    public UserInfoDto toDto(final User user) {
        return new UserInfoDto(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail());
    }
}
