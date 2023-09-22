package com.github.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/settings")
public class UserSettingsController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getSettings() {
        return "profile/settings";
    }
}
