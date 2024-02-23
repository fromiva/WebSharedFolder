package ru.fromiva.wsf.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController {

    /**
     * Returns template name.
     * @return page template name
     */
    @GetMapping
    public String getHelpPage() {
        return "help";
    }
}
