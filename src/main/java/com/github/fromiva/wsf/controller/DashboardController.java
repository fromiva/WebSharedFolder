package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController extends AbstractController {
    private final String page = "dashboard";
    @GetMapping
    public String getDashboardPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
