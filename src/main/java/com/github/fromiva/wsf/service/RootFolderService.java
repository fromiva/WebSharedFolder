package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.model.RootFolder;

import java.util.List;
import java.util.Optional;

/** Interface to handle {@code RootFolder} specific business logic. */
public interface RootFolderService {

    /**
     * Returns number of all the entities in the repository.
     * @return number of all the entities
     */
    long count();

    /**
     * Checks that the {@code RootFolder} with provided ID exists in the repository.
     * @param id ID of the {@code RootFolder} to check
     * @return {@code true} if the {@code RootFolder} exists, {@code false} otherwise
     */
    boolean existsById(Long id);

    /**
     * Checks that the {@code RootFolder} with provided name exists in the repository.
     * @param name name of the {@code RootFolder} to check
     * @return {@code true} if the {@code RootFolder} exists, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Checks that the {@code RootFolder} with provided path exists in the repository.
     * @param path path of the {@code RootFolder} to check
     * @return {@code true} if the {@code RootFolder} exists, {@code false} otherwise
     */
    boolean existsByPath(String path);

    /**
     * Finds for the {@code RootFolder} with provided ID in the repository.
     * @param id ID of the {@code RootFolder} to search
     * @return {@code Optional} with search result or empty {@code Optional} if nothing found
     */
    Optional<RootFolder> findById(Long id);

    /**
     * Finds for the {@code RootFolder} with provided name in the repository.
     * @param name of the {@code RootFolder} to search
     * @return {@code Optional} with the search result or empty {@code Optional} if nothing found
     */
    Optional<RootFolder> findByName(String name);

    /**
     * Finds for the {@code RootFolder} with provided path in the repository.
     * @param path of the {@code RootFolder} to search
     * @return {@code Optional} with the search result or empty {@code Optional} if nothing found
     */
    Optional<RootFolder> findByPath(String path);

    /**
     * Finds for all the RootFolders in the repository.
     * @return {@code List} with the search result or empty {@code List} if nothing found
     */
    List<RootFolder> findAll();

    /**
     * Saves a {@code RootFolder} in the repository.
     * @param rootFolder entity to save
     * @return original {@code RootFolder} object with the actual ID
     */
    RootFolder save(RootFolder rootFolder);

    /**
     * Updates a {@code RootFolder} in the repository.
     * @param rootFolder entity to update
     * @return {@code true} if success or {@code false} otherwise
     */
    RootFolder update(RootFolder rootFolder);

    /**
     * Removes a {@code RootFolder} with provided ID from the repository.
     * @param id ID of the {@code RootFolder} to remove
     */
    void deleteById(Long id);

    /**
     * Deletes all the entities from the repository.
     */
    void deleteAll();
}
