package com.github.fromiva.wsf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

public abstract class AbstractController {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.application.version}")
    private String applicationVersion;
    @Value("${spring.application.licence}")
    private String applicationLicence;
    @Value("${spring.application.url}")
    private String applicationUrl;

    void addTemplateParameters(Model model, String page) {
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("applicationVersion", applicationVersion);
        model.addAttribute("applicationLicence", applicationLicence);
        model.addAttribute("applicationUrl", applicationUrl);
        model.addAttribute("activePage", page);
        model.addAttribute("pageTitle", page.substring(0, 1).toUpperCase() + page.substring(1));
    }
}
