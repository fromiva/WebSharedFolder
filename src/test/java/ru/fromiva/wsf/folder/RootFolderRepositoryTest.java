package ru.fromiva.wsf.folder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fromiva.wsf.security.User;
import ru.fromiva.wsf.security.UserRepository;
import ru.fromiva.wsf.security.UserSecurityRole;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RootFolderRepositoryTest {

    /** Stub User to satisfy RootFolder table User ID reference constraints. */
    private static User user = new User(
            0L, "user@example.com", "{noop}password",
            "Stub", null, "User",
            true, true, true, true, UserSecurityRole.USER);

    /** {@code User}-specific Spring Data JPA repository. */
    @Autowired
    private UserRepository userRepository;

    /** {@code RootFolder}-specific Spring Data JPA repository. */
    @Autowired
    private RootFolderRepository rootFolderRepository;

    @BeforeEach
    void createStubUser() {
        user = userRepository.save(user);
    }

    @AfterEach
    void clearRepositoriesRecords() {
        rootFolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByNameWhenExistsThenGetTheSame() {
        RootFolder rootFolder = new RootFolder(
                0L, "Folder", "/home", 0L, "Folder description", user.getId());
        RootFolder expected = rootFolderRepository.save(rootFolder);
        Optional<RootFolder> actual = rootFolderRepository.findByName(expected.getName());
        assertThat(Optional.of(expected)).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void findByNameWhenNotExistsThenEmptyOptional() {
        String name = "Folder";
        assertThat(rootFolderRepository.findByName(name)).isEmpty();
    }

    @Test
    void findByPathWhenExistsThenGetTheSame() {
        RootFolder rootFolder = new RootFolder(
                0L, "Folder", "/home", 0L, "Folder description", user.getId());
        RootFolder expected = rootFolderRepository.save(rootFolder);
        Optional<RootFolder> actual = rootFolderRepository.findByPath(expected.getPath());
        assertThat(Optional.of(expected)).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void findByPathWhenNotExistsThenEmptyOptional() {
        String path = "/home";
        assertThat(rootFolderRepository.findByName(path)).isEmpty();
    }

    @Test
    void existsByNameWhenExistsThenTrue() {
        RootFolder rootFolder = new RootFolder(
                0L, "Folder", "/home", 0L, "Folder description", user.getId());
        RootFolder expected = rootFolderRepository.save(rootFolder);
        assertThat(rootFolderRepository.existsByName(expected.getName())).isTrue();
    }

    @Test
    void existsByNameWhenNotExistsThenFalse() {
        String name = "Folder";
        assertThat(rootFolderRepository.existsByName(name)).isFalse();
    }

    @Test
    void existsByPathWhenExistsThenTrue() {
        RootFolder rootFolder = new RootFolder(
                0L, "Folder", "/home", 0L, "Folder description", user.getId());
        RootFolder expected = rootFolderRepository.save(rootFolder);
        assertThat(rootFolderRepository.existsByPath(expected.getPath())).isTrue();
    }

    @Test
    void existsByPathWhenNotExistsThenFalse() {
        String path = "/home";
        assertThat(rootFolderRepository.existsByName(path)).isFalse();
    }
}
