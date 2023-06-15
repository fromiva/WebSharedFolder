package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController {
    private final String page = "search";
    @GetMapping
    public String getSearchPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
