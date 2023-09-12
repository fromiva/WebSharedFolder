package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.dto.PathDto;
import com.github.fromiva.wsf.dto.PathDtoMapper;
import com.github.fromiva.wsf.util.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class FolderRepositoryImpl implements FolderRepository {

    /** Path data transfer object mapper to prepare information about folder content. */
    private final PathDtoMapper pathDtoMapper;

    /** {@inheritDoc} */
    @Override
    public List<PathDto> getContent(final Path rootPath,
                                    final String rootAlias,
                                    final String relative)
            throws IOException, ElementNotFoundException {
        Path path = rootPath.resolve(relative);
        if (!Files.exists(path)) {
            throw new ElementNotFoundException("Filesystem request by incorrect path: " + path);
        }
        try (Stream<Path> stream = Files.list(path)) {
            return stream
                    .sorted()
                    .map(item -> pathDtoMapper.toDto(item, rootAlias, relative))
                    .toList();
        }
    }
}
