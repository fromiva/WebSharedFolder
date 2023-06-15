package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController extends AbstractController {
    private final String page = "help";
    @GetMapping
    public String getHelpPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
