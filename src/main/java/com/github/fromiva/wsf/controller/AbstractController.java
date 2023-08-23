package com.github.fromiva.wsf.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

public abstract class AbstractController {

    /** Application name from configuration file. */
    @Value("${spring.application.name}")
    private String applicationName;

    /** Application version from configuration file. */
    @Value("${spring.application.version}")
    private String applicationVersion;

    /** Application licence name from configuration file. */
    @Value("${spring.application.licence}")
    private String applicationLicence;

    /** Application URL from configuration file. */
    @Value("${spring.application.url}")
    private String applicationUrl;

    /**
     * Adds template parameters to the Model.
     * @param model Spring MVC utility object
     * @param page request page name
     */
    void addTemplateParameters(final Model model, final String page) {
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("applicationVersion", applicationVersion);
        model.addAttribute("applicationLicence", applicationLicence);
        model.addAttribute("applicationUrl", applicationUrl);
        model.addAttribute("activePage", page);
        model.addAttribute("pageTitle", page.substring(0, 1).toUpperCase() + page.substring(1));
    }
}
