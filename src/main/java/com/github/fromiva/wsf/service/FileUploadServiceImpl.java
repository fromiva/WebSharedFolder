package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.util.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Implementation class to handle file upload requests. */
@Service
@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    /** {@code RootFolder} specific service. */
    private final RootFolderService rootFolderService;

    /** {@inheritDoc} */
    @Override
    public void save(final String root, final String relative, final MultipartFile file)
            throws ElementNotFoundException, IOException {
        Path folder = Path.of(rootFolderService.getByName(root).getPath()).resolve(relative);
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            throw new ElementNotFoundException();
        }
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Incorrect file to save.");
        }
        Path destination = folder.resolve(file.getOriginalFilename());
        try (InputStream source = file.getInputStream()) {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
