package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/folders")
public class FoldersController extends AbstractController {
    private final String page = "folders";
    @GetMapping
    public String getFoldersPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
