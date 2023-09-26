package com.github.fromiva.wsf.controller;

import com.github.fromiva.wsf.dto.FolderBreadcrumbDtoMapper;
import com.github.fromiva.wsf.service.FileUploadService;
import com.github.fromiva.wsf.util.ElementNotFoundException;
import com.github.fromiva.wsf.util.Encoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
@AllArgsConstructor
public class FileUploadController {

    /** File upload specific service. */
    private final FileUploadService fileUploadService;

    /** Encoder and decoder a path from filename format to URL format and vice versa. */
    private final Encoder<String, String> pathToUrlEncoder;

    /** Data transfer object mapper to prepare breadcrumb navigation string. */
    private final FolderBreadcrumbDtoMapper folderBreadcrumbDtoMapper;

    /**
     * Returns template name for the file upload form.
     * @param model Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias} encoded in the URL format
     * @return page template name
     */
    @GetMapping("/{rootAlias}/{*relative}")
    public String getUpload(final Model model,
                            @PathVariable final String rootAlias,
                            @PathVariable final String relative) {
        model.addAttribute("url", pathToUrlEncoder.encode(rootAlias + relative));
        model.addAttribute("breadcrumbs",
                folderBreadcrumbDtoMapper.toDtoList(rootAlias + relative));
        return "folderUpload";
    }

    /**
     * Handles request to upload files.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param rootAlias name (alias) of a root folder
     * @param relative folder path relative to the {@code rootAlias} encoded in the URL format
     * @param files uploaded files to save in the specified folder
     * @return redirection rule if request performed successfully
     */
    @PostMapping("/{rootAlias}/{*relative}")
    public String postUpload(final Model model,
                             final RedirectAttributes redirectAttributes,
                             @PathVariable final String rootAlias,
                             @PathVariable final String relative,
                             @RequestParam final MultipartFile[] files) {
        int count = 0;
        String current = "";
        try {
            for (MultipartFile file : files) {
                current = file.getOriginalFilename();
                fileUploadService.save(rootAlias, relative, file);
                count++;
            }
        } catch (ElementNotFoundException exception) {
            model.addAttribute("alertDanger", "Folder '" + rootAlias + relative + "' not found.");
            return "errors/message";
        } catch (Exception exception) {
            model.addAttribute("alertDanger",
                    count + " file(s) uploaded. Unexpected error while upload " + current);
            return "errors/message";
        }
        redirectAttributes.addFlashAttribute("alertSuccess",
                count + " file(s) uploaded successfully.");
        return "redirect:/folders/" + pathToUrlEncoder.encode(rootAlias + relative);
    }
}
