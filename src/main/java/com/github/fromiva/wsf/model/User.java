package com.github.fromiva.wsf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

/**
 * Model class with information about User.
 * Includes support for Spring Security authentication mechanism.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity(name = "User")
@Table(name = "USERS")
@SuppressWarnings("checkstyle:MagicNumber")
public final class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Unique User ID, generated by the persistent storage. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            updatable = false,
            insertable = false)
    private Long id;

    /** User email. Unique and used as a username. */
    @EqualsAndHashCode.Include
    @Column(name = "EMAIL",
            nullable = false,
            unique = true,
            length = 256)
    private String email;

    /** User password. */
    @Column(name = "PASSWORD",
            nullable = false,
            length = 256)
    private String password;

    /** User's first name. */
    @Column(name = "FIRST_NAME",
            nullable = false,
            length = 64)
    private String firstName;

    /** User's middle name or patronymic. */
    @Column(name = "MIDDLE_NAME",
            length = 64)
    private String middleName;

    /** User's last name. */
    @Column(name = "LAST_NAME",
            nullable = false,
            length = 64)
    private String lastName;

    /** Whether user profile did not expire. */
    @Column(name = "ACCOUNT_NON_EXPIRED",
            nullable = false)
    private boolean accountNonExpired;

    /** Whether user profile did not lock. */
    @Column(name = "ACCOUNT_NON_LOCKED",
            nullable = false)
    private boolean accountNonLocked;

    /** Whether user profile credentials did not expire. */
    @Column(name = "CREDENTIAL_NON_EXPIRED",
            nullable = false)
    private boolean credentialsNonExpired;

    /** Whether user profile enabled. */
    @Column(name = "ENABLED",
            nullable = false)
    private boolean enabled;

    /** User security role to authorize access to Spring Web MVC controllers. */
    @Enumerated(EnumType.STRING)
    @Column(name = "SECURITY_ROLE",
            nullable = false)
    private UserSecurityRole userSecurityRole;

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Stub method to implement UserDetails interface.
     * Subject to change in future implementations.
     * @return Always return an empty unmodifiable set of
     * user granted security authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(userSecurityRole);
    }
}
