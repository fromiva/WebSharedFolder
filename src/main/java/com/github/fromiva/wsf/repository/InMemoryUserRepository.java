package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Stub class to support in memory authentication security mechanism.
 * Subject to change by a JPA based one in the future.
 */
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
public class InMemoryUserRepository implements UserRepository {

    /** Collection to store entities. */
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    /** Cursor to store ID of the next entity to put. */
    private final AtomicLong nextId = new AtomicLong(1L);

    {
        /* Stub user account. */
        save(new User(0L, "user@domain.net", "{noop}password",
                "Name", null, "Surname", Set.of(), true, true, true, true));
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public boolean existsById(final Long id) {
        return users.containsKey(id);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return users.values().stream()
                .anyMatch(user -> username.equals(user.getEmail()));
    }

    @Override
    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return users.values().stream()
                .filter(user -> username.equals(user.getEmail()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public Optional<User> save(final User user) {
        synchronized (users) {
            if (findByUsername(user.getEmail()).isEmpty()) {
                user.setId(nextId.getAndIncrement());
                users.put(user.getId(), user);
                return Optional.of(user);
            }
        }
        return Optional.empty();

    }

    @Override
    public boolean update(final User user) {
        return users.replace(user.getId(), user) != null;
    }

    @Override
    public void deleteById(final Long id) {
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
