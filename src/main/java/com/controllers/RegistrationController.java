package com.controllers;

import com.domain.Role;
import com.domain.User;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping(value = "/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    @GetMapping
    public String getRegisterPage() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user,
                          Map<String, Object> message) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            message.put("regStatusMessage", "User already exist");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);
        message.put("regStatusMessage", "Registration success");
        return "registration";
    }
}
