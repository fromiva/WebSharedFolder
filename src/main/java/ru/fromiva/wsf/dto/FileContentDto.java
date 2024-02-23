package ru.fromiva.wsf.dto;

import org.springframework.core.io.Resource;

/**
 * Utility class to transfer information about file content.
 * @param filename name of the file
 * @param mediaType a format type of the specified file in the MIME format according to RFC 2046
 * @param size file size in bytes
 * @param resource Resource representation for the file content,
 *                 allows accessing it with Java Stream API.
 */
public record FileContentDto(String filename,
                             String mediaType,
                             long size,
                             Resource resource) { }
