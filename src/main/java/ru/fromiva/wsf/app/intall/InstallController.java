package ru.fromiva.wsf.app.intall;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fromiva.wsf.security.SecurityService;
import ru.fromiva.wsf.security.User;

@Controller
@RequestMapping("install")
@AllArgsConstructor
public class InstallController {

    /** Security-specific service to support Spring Security business logic. */
    private final SecurityService securityService;

    /**
     * Returns template name for the initial application configuration.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getInstall(final Model model) {
        if (isConfigured()) {
            return "redirect:/";
        }
        model.addAttribute("page", "Install");
        model.addAttribute("user", new User());
        return "install/install";
    }

    /**
     * Handles request to register new user.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param user Spring Web MVC HTML form binding object
     * @param password user password (confirmation form input)
     * @return redirection rule if request performed successfully
     */
    @PostMapping
    public String postInstall(final Model model,
                              final RedirectAttributes redirectAttributes,
                              @ModelAttribute final User user,
                              @RequestParam("passwordConfirm") final String password) {
        if (isConfigured()) {
            return "redirect:/";
        }
        if (!user.getPassword().equals(password)) {
            model.addAttribute("alertWarning", "Please, type the same password twice.");
            model.addAttribute("user", user);
            return "install/install";
        }
        try {
            securityService.createNewSuperuser(
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword());
        } catch (Exception exception) {
            model.addAttribute("alertDanger", "Installation error.");
            model.addAttribute("user", user);
            return "install/install";
        }
        redirectAttributes.addFlashAttribute("alertSuccess",
                "You created superuser successfully and may login.");
        return "redirect:users/login";
    }

    /**
     * Checks that application is already configured.
     * @return {@code true} if already configured, {@code false} otherwise
     */
    private boolean isConfigured() {
        return securityService.isSuperuserConfigured();
    }
}
