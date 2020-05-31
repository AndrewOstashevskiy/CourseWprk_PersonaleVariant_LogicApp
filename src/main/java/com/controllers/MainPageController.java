package com.controllers;

import com.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {

    @GetMapping
    public String getMainPage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        return "generalPage";
    }
}
