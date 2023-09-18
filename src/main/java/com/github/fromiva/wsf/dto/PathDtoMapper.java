package com.github.fromiva.wsf.dto;

import com.github.fromiva.wsf.util.Encoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;

import static java.time.LocalDateTime.ofInstant;

/** Mapper to support {@code PathDto} data transfer object. */
@Component
@AllArgsConstructor
public final class PathDtoMapper {

    /** Encoder and decoder a path from filename format to URL format and vice versa. */
    private final Encoder<String, String> pathToUrlEncoder;

    /**
     * Produces {@code PathDto} item.
     * @param path item absolute path
     * @param rootAlias name (alias) of a root folder
     * @param relative item path relative to the {@code rootAlias}
     * @return {@code PathDto} item
     */
    public PathDto toDto(final Path path, final String rootAlias, final String relative) {
        BasicFileAttributes attributes;
        try {
            attributes = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException exception) {
            throw new RuntimeException("IOException when access to: " + path, exception);
        }
        String filename = path.getFileName().toString();
        return new PathDto(
                rootAlias,
                pathToUrlEncoder.encode(rootAlias + "/"
                        + relative + ("".equals(relative) ? "" : "/")
                        + filename),
                filename,
                getExtension(filename),
                attributes.isDirectory(),
                attributes.isRegularFile(),
                attributes.isSymbolicLink(),
                attributes.size(),
                ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault()),
                ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault()));
    }

    private String getExtension(final String name) {
        String[] parts = name.split("\\.");
        return parts.length == 1 ? "" : parts[parts.length - 1].toLowerCase();
    }
}
