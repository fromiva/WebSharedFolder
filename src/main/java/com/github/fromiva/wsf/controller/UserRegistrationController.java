package com.github.fromiva.wsf.controller;

import com.github.fromiva.wsf.model.User;
import com.github.fromiva.wsf.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users/signup")
@AllArgsConstructor
public class UserRegistrationController {

    /** Security-specific service to support Spring Security business logic. */
    private final SecurityService securityService;

    /**
     * Configs MVC attributes and returns template name.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getSignup(final Model model) {
        model.addAttribute("page", "Signup");
        model.addAttribute("user", new User());
        return "profile/signup";
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
    public String postSignup(final Model model,
                             final RedirectAttributes redirectAttributes,
                             @ModelAttribute final User user,
                             @RequestParam("passwordConfirm") final String password) {
        if (!user.getPassword().equals(password)) {
            model.addAttribute("alertWarning", "Please, type the same password twice.");
            model.addAttribute("user", user);
            return "profile/signup";
        }
        try {
            securityService.createNewUser(
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword());
        } catch (Exception exception) {
            model.addAttribute("alertDanger", "Registration error.");
            model.addAttribute("user", user);
            return "profile/signup";
        }
        redirectAttributes.addFlashAttribute("alertSuccess",
                "You are registered successfully and may login after your account activation.");
        return "redirect:login";
    }
}
