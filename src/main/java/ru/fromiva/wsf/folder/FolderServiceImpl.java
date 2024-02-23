package ru.fromiva.wsf.folder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fromiva.wsf.app.util.ElementNotFoundException;

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

    /** {@inheritDoc} */
    @Override
    public void create(final String rootAlias, final String relative, final String name)
            throws IOException, ElementNotFoundException {
        Path root = Path.of(rootFolderService.getByName(rootAlias).getPath());
        folderRepository.create(root, relative, name);
    }
}
