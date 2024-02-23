package ru.fromiva.wsf.folder;

import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Mapper to support {@code FileContentDto} data transfer object. */
@Component
public class FileContentDtoMapper {

    /**
     * Produces {@code FileContentDto} item.
     * @param file source data object
     * @return {@code FileContentDto} item
     */
    public FileContentDto toDto(final Path file) throws IOException {
        return new FileContentDto(
                file.getFileName().toString(),
                getMediaType(file),
                Files.size(file),
                new PathResource(file));
    }

    /**
     * Retrieves a format type of the specified file.
     * @param file to process
     * @return {@code String} representation in the MIME format according to RFC 2046
     * @throws IOException if an I/O error occurs
     */
    private String getMediaType(final Path file) throws IOException {
        String mediaType = Files.probeContentType(file);
        return mediaType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaType;
    }
}
