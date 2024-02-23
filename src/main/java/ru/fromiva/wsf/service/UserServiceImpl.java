package ru.fromiva.wsf.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fromiva.wsf.dto.UserInfoDto;
import ru.fromiva.wsf.model.User;
import ru.fromiva.wsf.model.UserSecurityRole;
import ru.fromiva.wsf.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/** Implementation class to handle {@code User} specific business logic. */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /** Repository interface to process requests. */
    private final UserRepository userRepository;

    /** {@inheritDoc} */
    @Override
    public long count() {
        return userRepository.count();
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsByUsername(final String username) {
        return userRepository.existsByEmail(username);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<User> findById(final Long id) {
        return userRepository.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<User> findByUsername(final String username) {
        return userRepository.findByEmail(username);
    }

    /** {@inheritDoc} */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public List<User> findAllActive() {
        return userRepository.findAllActive();
    }

    /** {@inheritDoc} */
    @Override
    public List<User> findAllByUserSecurityRole(final UserSecurityRole role) {
        return userRepository.findAllByUserSecurityRole(role);
    }

    /** {@inheritDoc} */
    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    /** {@inheritDoc} */
    @Override
    public User update(final User user) {
        return userRepository.save(user);
    }

    /** {@inheritDoc} */
    @Override
    public boolean updateFullName(final Long id, final UserInfoDto dto) {
        return userRepository.updateFullName(id,
                dto.firstName(), dto.middleName(), dto.lastName()) > 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean updateEmail(final Long id, final String email) {
        return userRepository.updateEmail(id, email) > 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean updatePassword(final Long id, final String password) {
        return userRepository.updatePassword(id, password) > 0;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
