package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController extends AbstractController {
    private final String page = "users";
    @GetMapping
    public String getUsersPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
