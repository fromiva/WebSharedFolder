package ru.fromiva.wsf.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getGroupsPage() {
        return "groups";
    }
}
