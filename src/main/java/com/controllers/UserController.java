package com.controllers;

import com.domain.User;
import com.repositories.UserRepository;
import com.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String userList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userRepository.
                findAllByUsernameIsNotAndPasswordIsNot(
                user.getUsername(),
                user.getPassword()));
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        userService.setDataForUserEditForm(user, model);
        return "userEdit";
    }



    @PostMapping
    public String userSave(@RequestParam Map<String, String> form,
                           @RequestParam("userId") User user
                           ) {

        userService.editUserData(form, user);
        return "redirect:/user";
    }


}
