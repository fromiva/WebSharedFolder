package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
    /**
     * Web page title.
     */
    private final String page = "login";

    /**
     * Returns login form html page.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping("login")
    public String getLogin(final Model model) {
        addTemplateParameters(model, page);
        return "login";
    }
}
