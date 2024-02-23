package ru.fromiva.wsf.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fromiva.wsf.folder.IncorrectPathException;
import ru.fromiva.wsf.folder.RootFolder;
import ru.fromiva.wsf.folder.RootFolderService;
import ru.fromiva.wsf.util.ElementNotFoundException;

@Controller
@RequestMapping("admin/roots")
@AllArgsConstructor
public class RootFolderAdminController {

    /** Service to handle {@code RootFolder} specific business logic. */
    private final RootFolderService rootFolderService;

    /**
     * Returns template name with a root folder settings list.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getRootFolderList(final Model model) {
        model.addAttribute("folders", rootFolderService.getAll());
        return "admin/rootFolders";
    }

    /**
     * Returns template name with root folder settings page.
     * @param model Spring Web MVC utility class
     * @param id the root folder id to retrieve
     * @return page template name
     */
    @GetMapping("{id}")
    public String getRootFolderProperties(final Model model, @PathVariable final Long id) {
        RootFolder root;
        try {
            root = rootFolderService.getById(id);
        } catch (ElementNotFoundException exception) {
            model.addAttribute("alertDanger", "Root folder with ID " + id + " not found.");
            return "errors/message";
        }
        model.addAttribute("root", root);
        return "admin/rootFolderProperties";
    }

    /**
     * Handles request to change root folder settings.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param edited Spring Web MVC HTML form binding object
     * @return redirection rule if request performed successfully
     */
    @PostMapping
    public String postRootFolderProperties(final Model model,
                                   final RedirectAttributes redirectAttributes,
                                   @ModelAttribute final RootFolder edited) {
        try {
            rootFolderService.getById(edited.getId());
        } catch (ElementNotFoundException exception) {
            model.addAttribute("alertDanger",
                    "Root folder with ID " + edited.getId() + " not found.");
            return "errors/message";
        }
        try {
            rootFolderService.update(edited);
        } catch (IncorrectPathException exception) {
            model.addAttribute("root", edited);
            model.addAttribute("alertDanger", "Path not found. Please provide a correct one.");
            return "admin/rootFolderProperties";
        } catch (Exception exception) {
            model.addAttribute("alertDanger", "Unexpected error.");
            return "errors/message";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Root folder edited successfully");
        return "redirect:/admin/roots";
    }

    /**
     * Returns template name with page to create a new root folder.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping("new")
    public String getRootFolderCreate(final Model model) {
        model.addAttribute("root", new RootFolder());
        return "admin/rootFolderCreate";
    }

    /**
     * Handles request to create a new root folder.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param rootFolder Spring Web MVC HTML form binding object
     * @return redirection rule if request performed successfully
     */
    @PostMapping("new")
    public String postRootFolderCreate(final Model model,
                                       final RedirectAttributes redirectAttributes,
                                       @ModelAttribute final RootFolder rootFolder) {
        try {
            rootFolderService.create(
                    rootFolder.getName(),
                    rootFolder.getPath(),
                    rootFolder.getLimit(),
                    rootFolder.getDescription());
        } catch (IncorrectPathException exception) {
            model.addAttribute("root", rootFolder);
            model.addAttribute("alertDanger", "Path not found. Please provide a correct one.");
            return "admin/rootFolderCreate";
        } catch (Exception exception) {
            model.addAttribute("alertDanger", "Unexpected error.");
            return "errors/message";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Root folder created successfully");
        return "redirect:/admin/roots";
    }

    /**
     * Deletes root folder with provided {@code id}.
     * @param redirectAttributes Spring Web MVC utility class
     * @param id of the root folder to delete
     * @return redirection rule
     */
    @GetMapping("delete/{id}")
    public String deleteRootFolder(final RedirectAttributes redirectAttributes,
                                   @PathVariable final Long id) {
        redirectAttributes.addFlashAttribute("alertSuccess", "Root folder deleted successfully");
        rootFolderService.deleteById(id);
        return "redirect:/admin/roots";
    }
}
