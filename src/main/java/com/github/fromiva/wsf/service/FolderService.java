package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.PathDto;
import com.github.fromiva.wsf.util.ElementNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FolderService {

    /**
     * Retrieves the contents of the specified file system folder.
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias}
     * @return list of data transfer objects with the information about specified folder content
     * @throws IOException transferring from underlying file system repository
     * @throws ElementNotFoundException when root folder of relative address can't be found
     */
    List<PathDto> getContent(String rootAlias, String relative)
            throws IOException, ElementNotFoundException;
}
