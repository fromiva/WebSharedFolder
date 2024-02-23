package ru.fromiva.wsf.folder;

import java.time.LocalDateTime;

/**
 * Utility class to transfer information about filesystem items.
 * @param rootAlias name (alias) of a root folder
 * @param url relative path (include root alias) encoded to URL format
 * @param name of the item
 * @param extension of the item name
 * @param isDirectory whether the item is a directory
 * @param isFile whether the item is a regular file
 * @param isSymlink whether the item is a symbolic link
 * @param size of the item
 * @param createdAt contains the creation time.
 *                  The creation time is the time that the item was created
 * @param modifiedAt contains the modification time.
 *                   The modification time is the time that the item was modified for the last time
 */
public record PathDto(String rootAlias,
                      String url,
                      String name,
                      String extension,
                      boolean isDirectory,
                      boolean isFile,
                      boolean isSymlink,
                      long size,
                      LocalDateTime createdAt,
                      LocalDateTime modifiedAt) { }
