package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.UserInfoDto;
import com.github.fromiva.wsf.dto.UserInfoDtoMapper;
import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.model.UserSecurityRole;
import com.github.fromiva.wsf.util.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Implementation class to handle security-specific business logic. */
@Service
public class SecurityServiceImpl implements SecurityService {

    /** Service to handle {@code User} specific business logic. */
    private final UserService userService;

    /** Mapper to support {@code UserInfoDto} data transfer object. */
    private final UserInfoDtoMapper userNameDtoMapper;

    /** Password encoder to support password encryption / decryption Spring Security mechanism. */
    private final PasswordEncoder passwordEncoder;

    /** Enables / disables auto activation of the new user's account. */
    private final boolean confirmRegistration;

    /**
     * All arguments constructor.
     * @param userService service bean to handle {@code User} specific business logic
     * @param userNameDtoMapper mapper bean to support {@code UserInfoDto} data transfer object
     * @param passwordEncoder password encoder bean to support password encryption / decryption
     * @param confirmRegistration flag to confirm activation of the new user's account,
     *                            value from application properties file
     */
    public SecurityServiceImpl(final UserService userService,
                               final UserInfoDtoMapper userNameDtoMapper,
                               final PasswordEncoder passwordEncoder,
                               @Value("${wsf.application.security.registration-confirm}")
                               final boolean confirmRegistration) {
        this.userService = userService;
        this.userNameDtoMapper = userNameDtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.confirmRegistration = confirmRegistration;
    }

    /** {@inheritDoc} */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userService.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

    /** {@inheritDoc} */
    @Override
    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /** {@inheritDoc} */
    @Override
    public Long getPrincipalId() {
        return getPrincipal().getId();
    }

    /** {@inheritDoc} */
    @Override
    public UserInfoDto getPrincipalName() {
        return userNameDtoMapper.toDto(getPrincipal());
    }

    /** {@inheritDoc} */
    @Override
    public void refreshPrincipal() {
        Long id = getPrincipalId();
        User user = userService.findById(id).orElseThrow(IllegalStateException::new);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /** {@inheritDoc} */
    @Override
    public boolean changePrincipalFullName(final UserInfoDto dto) {
        boolean result = userService.updateFullName(getPrincipalId(), dto);
        refreshPrincipal();
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean changePrincipalEmail(final String email, final String password)
            throws IncorrectPasswordException {
        if (!passwordEncoder.matches(password, getPrincipal().getPassword())) {
            throw  new IncorrectPasswordException();
        }
        boolean result = userService.updateEmail(getPrincipalId(), email);
        refreshPrincipal();
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean changePrincipalPassword(final String newPassword, final String oldPassword)
            throws IncorrectPasswordException {
        if (!passwordEncoder.matches(oldPassword, getPrincipal().getPassword())) {
            throw  new IncorrectPasswordException();
        }
        boolean result = userService
                .updatePassword(getPrincipalId(), passwordEncoder.encode(newPassword));
        refreshPrincipal();
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPrincipalAnAdmin() {
        UserSecurityRole role = getPrincipal().getUserSecurityRole();
        return role == UserSecurityRole.ROOT_ADMIN || role == UserSecurityRole.ADMIN;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPrincipalARootAdmin() {
        UserSecurityRole role = getPrincipal().getUserSecurityRole();
        return role == UserSecurityRole.ROOT_ADMIN;
    }

    /** {@inheritDoc} */
    @Override
    public void createNewUser(final String firstName,
                              final String middleName,
                              final String lastName,
                              final String email,
                              final String password) {
        User user = new User(
                0L, email, passwordEncoder.encode(password),
                firstName, middleName, lastName,
                true, true, true, !confirmRegistration,
                UserSecurityRole.USER);
        userService.save(user);
    }

    /** {@inheritDoc} */
    @Override
    public void createNewSuperuser(final String firstName,
                                   final String middleName,
                                   final String lastName,
                                   final String email,
                                   final String password) {
        User user = new User(
                0L, email, passwordEncoder.encode(password),
                firstName, middleName, lastName,
                true, true, true, true,
                UserSecurityRole.ROOT_ADMIN);
        userService.save(user);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSuperuserConfigured() {
        return !userService.findAllByUserSecurityRole(UserSecurityRole.ROOT_ADMIN).isEmpty();
    }
}
