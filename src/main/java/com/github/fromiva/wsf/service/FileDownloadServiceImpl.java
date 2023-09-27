package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.FileContentDto;
import com.github.fromiva.wsf.dto.FileContentDtoMapper;
import com.github.fromiva.wsf.util.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Implementation class to handle file download requests. */
@Service
@AllArgsConstructor
public class FileDownloadServiceImpl implements FileDownloadService {

    /** {@code RootFolder} specific service. */
    private final RootFolderService rootFolderService;

    /** File content data transfer object mapper to prepare information about file content. */
    private final FileContentDtoMapper fileContentDtoMapper;

    /** {@inheritDoc} */
    @Override
    public FileContentDto getContent(final String root, final String relative)
            throws ElementNotFoundException, IOException {
        Path file = Path.of(rootFolderService.getByName(root).getPath()).resolve(relative);
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            throw new ElementNotFoundException();
        }
        return fileContentDtoMapper.toDto(file);
    }
}
