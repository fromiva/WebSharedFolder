package com.github.fromiva.wsf.dto;

import com.github.fromiva.wsf.model.User;
import org.springframework.stereotype.Component;

/** Mapper to support {@code UserNameDto} data transfer object. */
@Component
public class UserNameDtoMapper {

    /**
     * Produces {@code UserNameDto} item.
     * @param user source data object
     * @return {@code UserNameDto} item
     */
    public UserNameDto toDto(final User user) {
        return new UserNameDto(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName());
    }
}
