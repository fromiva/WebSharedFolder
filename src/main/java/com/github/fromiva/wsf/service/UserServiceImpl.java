package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Implementation class to handle {@code User} specific business logic. */
@Service
@AllArgsConstructor
@SuppressWarnings("checkstyle:DesignForExtension")
public class UserServiceImpl implements UserService {

    /** Repository interface to process requests. */
    private final UserRepository userRepository;

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    public Optional<User> findById(final Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User \"%s\" not found", username)));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(final User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
