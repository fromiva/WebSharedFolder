package ru.fromiva.wsf.service;

import org.springframework.web.multipart.MultipartFile;
import ru.fromiva.wsf.util.ElementNotFoundException;

import java.io.IOException;

/** Interface to handle file upload requests. */
public interface FileUploadService {

    /**
     * Handles requests to save uploaded file content.
     * @param root the {@code RootFolder} alias to retrieve relative file
     * @param relative the file path relative to the {@code root}
     * @param file uploaded file to save in the specified folder
     * @throws ElementNotFoundException if the {@code RootFolder} with provided alias
     * or the relative folder path doesn't exist
     * @throws IOException if an I/O error occurs
     */
    void save(String root, String relative, MultipartFile file)
            throws ElementNotFoundException, IOException;
}
