package com.github.fromiva.wsf.controller;

import com.github.fromiva.wsf.dto.UserInfoDto;
import com.github.fromiva.wsf.service.SecurityService;
import com.github.fromiva.wsf.util.IncorrectPasswordException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users/profile")
@AllArgsConstructor
public class UserProfileController {

    /** Security-specific service to support Spring Security business logic. */
    private final SecurityService securityService;

    /**
     * Returns template name for the current principal (logged-in user) information.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getProfile(final Model model) {
        model.addAttribute("fullName", securityService.getPrincipalName());
        model.addAttribute("email", securityService.getPrincipal().getEmail());
        return "profile/profile";
    }

    /**
     * Returns template name to change the current principal (logged-in user) full name.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping("/username")
    public String getUsernameChange(final Model model) {
        model.addAttribute("fullName", securityService.getPrincipalName());
        return "profile/username";
    }

    /**
     * Handles request ro change fullname of the current principal (logged-in user).
     * @param redirectAttributes Spring Web MVC utility class
     * @param dto new user fullname from edit form
     * @return redirection rule if request performed successfully
     */
    @PostMapping("/username")
    public String postUsernameChange(final RedirectAttributes redirectAttributes,
                                     @ModelAttribute final UserInfoDto dto) {
        securityService.changePrincipalFullName(dto);
        redirectAttributes.addFlashAttribute("alertSuccess", "Full name changed successfully.");
        return "redirect:/users/profile";
    }

    /**
     * Returns template name to change the current principal (logged-in user) email.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping("/email")
    public String getEmailChange(final Model model) {
        model.addAttribute("email", securityService.getPrincipal().getEmail());
        return "profile/email";
    }

    /**
     * Handles request ro change email of the current principal (logged-in user).
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param email new user email from edit form
     * @param password user credentials to verify operation
     * @return redirection rule if request performed successfully
     */
    @PostMapping("/email")
    public String postEmailChange(final Model model,
                                  final RedirectAttributes redirectAttributes,
                                  @RequestParam final String email,
                                  @RequestParam final String password) {
        try {
            securityService.changePrincipalEmail(email, password);
        } catch (IncorrectPasswordException exception) {
            model.addAttribute("email", securityService.getPrincipal().getEmail());
            model.addAttribute("alertWarning", "Incorrect current password. Try again please");
            return "profile/email";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Email changed successfully.");
        return "redirect:/users/profile";
    }

    /**
     * Returns template name to change the current principal (logged-in user) password.
     * @return page template name
     */
    @GetMapping("/password")
    public String getPasswordChange() {
        return "profile/password";
    }

    /**
     * Handles request to change password of the current principal (logged-in user).
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param newPassword1 new user password from edit form
     * @param newPassword2 new user password from edit form (confirmation).
     *                     {@code newPassword1} and {@code newPassword2} should be equal
     * @param oldPassword user credentials to verify operation
     * @return redirection rule if request performed successfully
     */
    @PostMapping("/password")
    public String postPasswordChange(final Model model,
                                     final RedirectAttributes redirectAttributes,
                                     @RequestParam final String newPassword1,
                                     @RequestParam final String newPassword2,
                                     @RequestParam final String oldPassword) {
        if (!newPassword1.equals(newPassword2)) {
            model.addAttribute("alertWarning", "Please, type the same new password twice.");
            return "profile/password";
        }
        try {
            securityService.changePrincipalPassword(newPassword1, oldPassword);
        } catch (IncorrectPasswordException exception) {
            model.addAttribute("alertWarning", "Incorrect current password. Please, try again.");
            return "profile/password";
        }
        redirectAttributes.addFlashAttribute("alertSuccess", "Password changed successfully.");
        return "redirect:/users/profile";
    }

    /**
     * Utility method to handle unexpected exceptions.
     * @param redirectAttributes Spring Web MVC utility class
     * @param exception to handle
     * @return redirection rule
     */
    @ExceptionHandler
    public String unexpectedExceptionHandler(final RedirectAttributes redirectAttributes,
                                             final Exception exception) {
        redirectAttributes.addFlashAttribute("alertDanger", "Unexpected error: "
                + exception.getClass().getSimpleName());
        return "redirect:/users/profile";
    }
}
