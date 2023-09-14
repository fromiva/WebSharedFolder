package com.github.fromiva.wsf.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration with user security roles.
 * Supports Spring Security authorization mechanism.
 */
@AllArgsConstructor
public enum UserSecurityRole implements GrantedAuthority {

    /** Root admin user security role. */
    ROOT_ADMIN,

    /** Average admin user security role. */
    ADMIN,

    /** Regular user security role. */
    USER;

    /**
     * Implementation of the {@code GrantedAuthority} interface
     * to support Spring Security authorization mechanism.
     * @return Spring Security role name
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
