package com.controllers;

import com.domain.Role;
import com.domain.UserDetails;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;

    @GetMapping("/reg")
    public String getRegisterPage() {
        return "registration";
    }

    @PostMapping("/reg")
    public String addUser(UserDetails user, Map<String, Object> message) {
        UserDetails userFromDb = userRepository.findByUserName(user.getUserName());

        if (userFromDb != null) {
            message.put("message", "User already exist");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);
        message.put("message", "Registration success");
        return "/registration";
    }
}
