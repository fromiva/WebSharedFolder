package ru.fromiva.wsf.folder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fromiva.wsf.util.ElementNotFoundException;
import ru.fromiva.wsf.util.Encoder;

@Controller
@RequestMapping("/api/folders")
@AllArgsConstructor
public class FolderApiController {

    /** File upload specific service. */
    private final FolderService folderService;

    /** Encoder and decoder a path from filename format to URL format and vice versa. */
    private final Encoder<String, String> pathToUrlEncoder;

    /** Data transfer object mapper to prepare breadcrumb navigation string. */
    private final FolderBreadcrumbDtoMapper folderBreadcrumbDtoMapper;

    /**
     * Returns template name for the new folder create form.
     * @param model Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias} encoded in the URL format
     * @return page template name
     */
    @GetMapping("create/{rootAlias}/{*relative}")
    public String getCreate(final Model model,
                            @PathVariable final String rootAlias,
                            @PathVariable final String relative) {
        model.addAttribute("url", pathToUrlEncoder.encode(rootAlias + relative));
        model.addAttribute("breadcrumbs",
                folderBreadcrumbDtoMapper.toDtoList(rootAlias + relative));
        return "folderCreate";
    }

    /**
     * Handles request to create a new folder in a root folder.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param name of the new folder
     * @return redirection rule if request performed successfully
     */
    @PostMapping("create/{rootAlias}")
    public String postCreate(final Model model,
                             final RedirectAttributes redirectAttributes,
                             @PathVariable final String rootAlias,
                             @RequestParam final String name) {
        return postCreateWithRelative(model, redirectAttributes, rootAlias, "/", name);
    }

    /**
     * Handles request to create a new folder.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias} encoded in the URL format
     * @param name of the new folder
     * @return redirection rule if request performed successfully
     */
    @PostMapping("create/{rootAlias}/{*relative}")
    public String postCreateWithRelative(final Model model,
                             final RedirectAttributes redirectAttributes,
                             @PathVariable final String rootAlias,
                             @PathVariable final String relative,
                             @RequestParam final String name) {
        try {
            folderService.create(rootAlias, relative.substring(1), name);
        } catch (ElementNotFoundException exception) {
            model.addAttribute("alertDanger", "Folder '" + rootAlias + relative + "' not found.");
            return "errors/message";
        } catch (Exception exception) {
            model.addAttribute("alertDanger",
                    "Unexpected error while creating folder " + name);
            return "errors/message";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Folder created successfully.");
        return "redirect:/folders/" + pathToUrlEncoder.encode(rootAlias + relative);
    }
}
