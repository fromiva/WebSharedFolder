package com.github.fromiva.wsf.repository;

import com.github.fromiva.wsf.model.RootFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** Interface to provide access to {@code RootFolder} specific repository. */
public interface RootFolderRepository extends JpaRepository<RootFolder, Long> {

    /**
     * Search RootFolder by name.
     * @param name root folder name to search
     * @return optional with RootFolder if found, empty optional otherwise
     */
    Optional<RootFolder> findByName(String name);

    /**
     * Search RootFolder by path.
     * @param path root folder path to search
     * @return optional with RootFolder if found, empty optional otherwise
     */
    Optional<RootFolder> findByPath(String path);

    /**
     * Checks that RootFolder with provided name actually exist.
     * @param name root folder name to check
     * @return {@code true} if RootFolder exists, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Checks that RootFolder with provided path actually exist.
     * @param path root folder path to check
     * @return {@code true} if RootFolder exists, {@code false} otherwise
     */
    boolean existsByPath(String path);
}
