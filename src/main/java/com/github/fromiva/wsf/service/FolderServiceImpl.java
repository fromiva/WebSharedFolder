package com.github.fromiva.wsf.service;

import com.github.fromiva.wsf.dto.PathDto;
import com.github.fromiva.wsf.model.RootFolder;
import com.github.fromiva.wsf.repository.FolderRepository;
import com.github.fromiva.wsf.util.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

    /** {@code RootFolder} specific service to obtain a root folder path by it alias. */
    private final RootFolderService rootFolderService;

    /** Repository to access to a file system content. */
    private final FolderRepository folderRepository;

    /** {@inheritDoc} */
    @Override
    public List<PathDto> getContent(final String rootAlias, final String relative)
            throws IOException, ElementNotFoundException {
        RootFolder rootFolder = rootFolderService.getByName(rootAlias);
        return folderRepository.getContent(Path.of(rootFolder.getPath()), rootAlias, relative);
    }
}
