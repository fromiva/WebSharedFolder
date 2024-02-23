package ru.fromiva.wsf.app.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/shutdown")
public class ShutdownController {

    /**
     * Returns template name with the HTML form to shut down the application.
     * @return page template name
     */
    @GetMapping
    public String getShutdown() {
        return "admin/shutdown";
    }
}
