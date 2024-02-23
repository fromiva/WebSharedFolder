package ru.fromiva.wsf.folder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.fromiva.wsf.util.ElementNotFoundException;
import ru.fromiva.wsf.util.Encoder;

import java.util.List;

@Controller
@RequestMapping("/folders")
@AllArgsConstructor
public class FolderController {

    /** {@code RootFolder} specific service. */
    private final RootFolderService rootFolderService;

    /** {@code Folder} specific service. */
    private final FolderService folderService;

    /** Encoder and decoder a path from filename format to URL format and vice versa. */
    private final Encoder<String, String> pathToUrlEncoder;

    /** Data transfer object mapper to prepare breadcrumb navigation string. */
    private final FolderBreadcrumbDtoMapper folderBreadcrumbDtoMapper;

    /**
     * Returns template name for the list of all the root folders.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getRootListPage(final Model model) {
        model.addAttribute("roots", rootFolderService.getAll());
        return "roots";
    }

    /**
     * Returns template name for the content of the specified root folder.
     * @param model Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @return page template name
     */
    @GetMapping("/{rootAlias}")
    public String getRootPage(final Model model, @PathVariable final String rootAlias) {
        return getFolderPage(model, rootAlias, "/");
    }

    /**
     * Returns template name for the folder content
     * specified by root folder name and relative address.
     * @param model Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias} encoded in the URL format
     * @return page template name
     */
    @GetMapping("/{rootAlias}/{*relative}")
    public String getFolderPage(final Model model,
                                @PathVariable final String rootAlias,
                                @PathVariable final String relative) {
        List<PathDto> content;
        try {
            content = folderService
                    .getContent(pathToUrlEncoder.decode(rootAlias), relative.substring(1));
        } catch (ElementNotFoundException exception) {
            model.addAttribute("alertDanger", "Folder '" + rootAlias + relative + "' not found.");
            return "errors/message";
        } catch (Exception exception) {
            model.addAttribute("alertDanger", "Unexpected error when access: "
                    + rootAlias + relative);
            return "errors/message";
        }
        if (content.isEmpty()) {
            model.addAttribute("alertInfo", "Folder has no content.");
        }
        model.addAttribute("url", pathToUrlEncoder.encode(rootAlias + relative));
        model.addAttribute("items", content);
        model.addAttribute("breadcrumbs",
                folderBreadcrumbDtoMapper.toDtoList(rootAlias + relative));
        return "folders";
    }
}
