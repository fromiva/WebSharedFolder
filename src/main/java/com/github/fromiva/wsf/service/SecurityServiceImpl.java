package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.UserNameDto;
import com.github.fromiva.wsf.dto.UserNameDtoMapper;
import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.util.IncorrectPasswordException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Implementation class to handle security-specific business logic. */
@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    /** Service to handle {@code User} specific business logic. */
    private final UserService userService;

    /** Mapper to support {@code UserNameDto} data transfer object. */
    private final UserNameDtoMapper userNameDtoMapper;

    /** Password encoder to support password encryption / decryption Spring Security mechanism. */
    private final PasswordEncoder passwordEncoder;

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
    public UserNameDto getPrincipalName() {
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
    public boolean changePrincipalFullName(final UserNameDto dto) {
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
}
