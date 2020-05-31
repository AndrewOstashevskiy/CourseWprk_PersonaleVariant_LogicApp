package com.controllers;

import com.domain.Role;
import com.domain.User;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(@RequestParam Map<String, String> form,
                           @RequestParam("userId") User user
                           ) {

        user.getRoles().clear();
        user.setUsername(form.get("username"));
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        form.keySet().forEach(s -> {
            if (roles.contains(s)) {
                user.getRoles().add(Role.valueOf(s));
            }
        });

        if (user.getRoles().isEmpty()) {
            user.getRoles().add(Role.USER);
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
