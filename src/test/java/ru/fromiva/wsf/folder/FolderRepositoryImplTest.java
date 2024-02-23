package ru.fromiva.wsf.folder;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AnnotatedElementContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.io.TempDirFactory;
import ru.fromiva.wsf.app.util.ElementNotFoundException;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FolderRepositoryImplTest {

    /** Repository under test. */
    private final FolderRepository folderRepository = new FolderRepositoryImpl(
            new PathDtoMapper(new PathToUrlEncoder()), new DelegatingPathComparator());

    @Test
    void whenGetNotExistingDirectoryThenException(
            @TempDir(factory = JimfsTempDirFactory.class) final Path tempDir) {
        String directory = "Directory";
        Path path = tempDir.resolve(directory);
        assertThat(Files.exists(path)).isFalse();
        assertThat(Files.isDirectory(path)).isFalse();
        assertThatThrownBy(
                () -> folderRepository.getContent(tempDir, tempDir.toString(), directory))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageStartingWith("Filesystem request by incorrect path");
    }

    @Test
    void whenGetExistingDirectoryWithoutContentThenEmptyList(
            @TempDir(factory = JimfsTempDirFactory.class) final Path tempDir)
            throws IOException, ElementNotFoundException {
        String directory = "Directory";
        Path path = tempDir.resolve(directory);
        Files.createDirectory(path);
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.isDirectory(path)).isTrue();
        assertThat(folderRepository.getContent(tempDir, tempDir.toString(), directory)).isEmpty();
    }

    @Test
    void whenGetExistingDirectoryWithContentThenList(
            @TempDir(factory = JimfsTempDirFactory.class) final Path tempDir)
            throws IOException, ElementNotFoundException {
        String relative = "Directory";
        String extension = "txt";
        String filename = "file" + "." + extension;
        Path path = tempDir.resolve(relative);
        Files.createDirectory(path);
        Path file = Files.createFile(path.resolve(filename));
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        PathDto expected = new PathDto(tempDir.toString(), file.toString(),
                filename, extension, false, true, false, 0L,
                ofInstant(attributes.creationTime().toInstant(), systemDefault()),
                ofInstant(attributes.lastModifiedTime().toInstant(), systemDefault()));
        assertThat(Files.exists(file)).isTrue();
        assertThat(Files.isRegularFile(file)).isTrue();
        assertThat(folderRepository.getContent(tempDir, tempDir.toString(), relative))
                .isEqualTo(List.of(expected));
    }

    static class JimfsTempDirFactory implements TempDirFactory {

        /** In-memory Java-based filesystem simulation. */
        private final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());

        @Override
        public Path createTempDirectory(final AnnotatedElementContext elementContext,
                final ExtensionContext extensionContext) throws IOException {
            return Files.createTempDirectory(fileSystem.getPath("/"), "junit");
        }

        @Override
        public void close() throws IOException {
            fileSystem.close();
        }
    }
}
