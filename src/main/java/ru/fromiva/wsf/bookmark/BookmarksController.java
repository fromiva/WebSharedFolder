package ru.fromiva.wsf.bookmark;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookmarks")
public class BookmarksController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getBookmarksPage() {
        return "bookmarks";
    }
}
