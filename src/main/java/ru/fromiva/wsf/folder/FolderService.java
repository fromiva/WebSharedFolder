package ru.fromiva.wsf.folder;

import ru.fromiva.wsf.app.util.ElementNotFoundException;

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

    /**
     * Creates a new file system folder.
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias}
     * @param name of the new folder
     * @throws IOException transferring from underlying file system repository
     * @throws ElementNotFoundException when root folder of relative address can't be found
     */
    void create(String rootAlias, String relative, String name)
            throws IOException, ElementNotFoundException;
}
