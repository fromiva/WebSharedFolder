package ru.fromiva.wsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * Redirects request to default page.
     * @return redirection rule
     */
    @GetMapping
    public String getDashboardPage() {
        return "redirect:folders";
    }
}
