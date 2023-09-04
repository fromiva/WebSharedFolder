package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.model.RootFolder;
import com.github.fromiva.wsf.repository.RootFolderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<RootFolder> findById(final Long id) {
        return rootFolderRepository.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<RootFolder> findByName(final String name) {
        return rootFolderRepository.findByName(name);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<RootFolder> findByPath(final String path) {
        return rootFolderRepository.findByPath(path);
    }

    /** {@inheritDoc} */
    @Override
    public List<RootFolder> findAll() {
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
