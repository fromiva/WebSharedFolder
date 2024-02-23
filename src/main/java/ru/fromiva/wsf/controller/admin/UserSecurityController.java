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
import ru.fromiva.wsf.security.User;
import ru.fromiva.wsf.security.UserByNameAscComparator;
import ru.fromiva.wsf.security.UserSecurityRole;
import ru.fromiva.wsf.security.UserService;

import java.util.Optional;

@Controller
@RequestMapping("admin/users")
@AllArgsConstructor
public class UserSecurityController {

    /** Service to handle {@code User} specific business logic. */
    private final UserService userService;

    /** User by full name ascending comparator. */
    private final UserByNameAscComparator comparator;

    /**
     * Returns template name with user's security settings list.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getUserList(final Model model) {
        model.addAttribute("users",
                userService.findAll()
                        .stream()
                        .sorted(comparator)
                        .toList());
        return "admin/users";
    }

    /**
     * Returns template name with user's security settings personal page.
     * @param model Spring Web MVC utility class
     * @param id user's id to retrieve
     * @return page template name
     */
    @GetMapping("{id}")
    public String getUserSecurity(final Model model, @PathVariable final Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            model.addAttribute("alertDanger", "User with ID " + id + " not found.");
            return "errors/message";
        }
        model.addAttribute("user", user.get());
        model.addAttribute("roles", UserSecurityRole.values());
        return "admin/userProperties";
    }

    /**
     * Handles request ro change user's security settings.
     * @param model Spring Web MVC utility class
     * @param redirectAttributes Spring Web MVC utility class
     * @param edited Spring Web MVC HTML form binding object
     * @return redirection rule if request performed successfully
     */
    @PostMapping
    public String postUserSecurity(final Model model,
                                   final RedirectAttributes redirectAttributes,
                                   @ModelAttribute final User edited) {
        Optional<User> original = userService.findById(edited.getId());
        if (original.isEmpty()) {
            model.addAttribute("alertDanger", "Incorrect user.");
            return "errors/message";
        }
        User user = original.get();
        user.setEnabled(edited.isEnabled());
        user.setAccountNonExpired(edited.isAccountNonExpired());
        user.setAccountNonLocked(edited.isAccountNonLocked());
        user.setUserSecurityRole(edited.getUserSecurityRole());
        userService.update(user);
        redirectAttributes.addFlashAttribute("alertSuccess", "User edited successfully");
        return "redirect:/admin/users";
    }
}
