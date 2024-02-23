package ru.fromiva.wsf.folder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.fromiva.wsf.security.SecurityService;
import ru.fromiva.wsf.util.ElementNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RootFolderServiceImplTest {

    /** Stub root folder. */
    private static final RootFolder ROOT = new RootFolder(
            1L, "Root folder name", "/home", 0L, "Description", 1L);

    /** Mock root folder repository. */
    @Mock private RootFolderRepository rootFolderRepository;

    /** Mock security service. */
    @Mock private SecurityService securityService;

    /** Service under test. */
    private RootFolderService rootFolderService;

    @BeforeEach
    void beforeEach() {
        rootFolderService = new RootFolderServiceImpl(rootFolderRepository, securityService);
    }

    @Test
    void whenGetByIdAndNotFoundThenGetException() {
        when(rootFolderRepository.findById(any(long.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> rootFolderService.getById(ROOT.getId()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageEndingWith("not found.");
    }

    @Test
    void whenGetByIdAndFoundThenGetRootFolder() throws ElementNotFoundException {
        when(rootFolderRepository.findById(ROOT.getId())).thenReturn(Optional.of(ROOT));
        assertThat(rootFolderService.getById(ROOT.getId())).isEqualTo(ROOT);
    }

    @Test
    void whenGetByNameAndNotFoundThenGetException() {
        when(rootFolderRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> rootFolderService.getByName(ROOT.getName()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageEndingWith("not found.");
    }

    @Test
    void whenGetByNameAndFoundThenGetRootFolder() throws ElementNotFoundException {
        when(rootFolderRepository.findByName(ROOT.getName())).thenReturn(Optional.of(ROOT));
        assertThat(rootFolderService.getByName(ROOT.getName())).isEqualTo(ROOT);
    }

    @Test
    void whenGetByPathAndNotFoundThenGetException() {
        when(rootFolderRepository.findByPath(any(String.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> rootFolderService.getByPath(ROOT.getPath()))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageEndingWith("not found.");
    }

    @Test
    void whenGetByPathAndFoundThenGetRootFolder() throws ElementNotFoundException {
        when(rootFolderRepository.findByPath(ROOT.getPath())).thenReturn(Optional.of(ROOT));
        assertThat(rootFolderService.getByPath(ROOT.getPath())).isEqualTo(ROOT);
    }
}
