package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController {

    /** Web page title. */
    private final String page = "search";

    /**
     * Configs MVC attributes and returns template name.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getSearchPage(final Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
