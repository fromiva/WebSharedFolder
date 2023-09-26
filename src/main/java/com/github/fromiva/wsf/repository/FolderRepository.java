package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.dto.PathDto;
import com.github.fromiva.wsf.util.ElementNotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FolderRepository {

    /**
     * Retrieves the contents of the specified file system folder.
     * @param rootPath path of a root folder
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootPath}
     * @return list of data transfer objects with the information about specified folder content
     * @throws IOException if an I/O error occurs when opening the directory,
     * transferring from the underlying Java NIO API
     * @throws ElementNotFoundException when relative address can't be found
     */
    List<PathDto> getContent(Path rootPath, String rootAlias, String relative)
            throws IOException, ElementNotFoundException;

    /**
     * Creates a new file system folder.
     * @param rootPath path of a root folder
     * @param relative folder path relative to the {@code rootPath}
     * @param name of the new folder
     * @throws IOException if an I/O error occurs
     * @throws ElementNotFoundException when relative address can't be found
     */
    void create(Path rootPath, String relative, String name)
            throws IOException, ElementNotFoundException;
}
