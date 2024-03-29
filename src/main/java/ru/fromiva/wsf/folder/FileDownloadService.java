package ru.fromiva.wsf.folder;

import ru.fromiva.wsf.app.util.ElementNotFoundException;

import java.io.IOException;

/** Interface to handle file download requests. */
public interface FileDownloadService {

    /**
     * Handles requests to get file content.
     * @param root the {@code RootFolder} alias to retrieve relative file
     * @param relative the file path relative to the {@code root}
     * @return data transfer object with the information about specified file and it content
     * @throws ElementNotFoundException if the {@code RootFolder} with provided alias
     * or the required file doesn't exist
     * @throws IOException if an I/O error occurs
     */
    FileContentDto getContent(String root, String relative)
            throws ElementNotFoundException, IOException;
}
