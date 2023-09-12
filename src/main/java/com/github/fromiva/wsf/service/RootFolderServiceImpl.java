package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.model.RootFolder;
import com.github.fromiva.wsf.repository.RootFolderRepository;
import com.github.fromiva.wsf.util.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** Implementation class to handle {@code RootFolder} specific business logic. */
@Service
@AllArgsConstructor
public class RootFolderServiceImpl implements RootFolderService {

    /** Repository interface to process requests. */
    private final RootFolderRepository rootFolderRepository;

    /** {@inheritDoc} */
    @Override
    public long count() {
        return rootFolderRepository.count();
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsById(final Long id) {
        return rootFolderRepository.existsById(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsByName(final String name) {
        return rootFolderRepository.existsByName(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsByPath(final String path) {
        return rootFolderRepository.existsByPath(path);
    }

    /** {@inheritDoc} */
    @Override
    public RootFolder getById(final Long id) throws ElementNotFoundException {
        return rootFolderRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(
                "Root folder with ID " + id + " not found."));
    }

    /** {@inheritDoc} */
    @Override
    public RootFolder getByName(final String name) throws ElementNotFoundException {
        return rootFolderRepository.findByName(name).orElseThrow(() -> new ElementNotFoundException(
                "Root folder with name '" + name + "' not found."));
    }

    /** {@inheritDoc} */
    @Override
    public RootFolder getByPath(final String path) throws ElementNotFoundException {
        return rootFolderRepository.findByPath(path).orElseThrow(() -> new ElementNotFoundException(
                "Root folder with path '" + path + "' not found."));
    }

    /** {@inheritDoc} */
    @Override
    public List<RootFolder> getAll() {
        return rootFolderRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public RootFolder save(final RootFolder rootFolder) {
        return rootFolderRepository.save(rootFolder);
    }

    /** {@inheritDoc} */
    @Override
    public RootFolder update(final RootFolder rootFolder) {
        return rootFolderRepository.save(rootFolder);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(final Long id) {
        rootFolderRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteAll() {
        rootFolderRepository.deleteAll();
    }
}
