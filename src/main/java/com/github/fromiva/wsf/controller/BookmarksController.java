package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookmarks")
public class BookmarksController extends AbstractController {
    private final String page = "bookmarks";
    @GetMapping
    public String getBookmarksPage(Model model) {
        addTemplateParameters(model, page);
        return page;
    }
}
