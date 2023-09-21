package com.github.fromiva.wsf.controller;

import com.github.fromiva.wsf.dto.UserInfoDtoMapper;
import com.github.fromiva.wsf.service.UserService;
import com.github.fromiva.wsf.util.UserByNameAscComparator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserListController {

    /** Service to handle {@code User} specific business logic. */
    private final UserService userService;

    /** Mapper to support {@code UserInfoDto} data transfer object. */
    private final UserInfoDtoMapper userInfoDtoMapper;

    /** User by full name ascending comparator. */
    private final UserByNameAscComparator comparator;

    /**
     * Returns template name.
     * @param model Spring Web MVC utility class
     * @return page template name
     */
    @GetMapping
    public String getUserListPage(final Model model) {
        model.addAttribute("users",
                userService.findAllActive()
                        .stream()
                        .sorted(comparator)
                        .map(userInfoDtoMapper::toDto)
                        .toList());
        return "users";
    }
}
