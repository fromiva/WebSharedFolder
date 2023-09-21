package com.github.fromiva.wsf.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getAdminPanel() {
        return "admin/panel";
    }
}
