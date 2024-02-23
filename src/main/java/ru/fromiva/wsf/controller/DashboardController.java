package ru.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getDashboardPage() {
        return "dashboard";
    }
}
