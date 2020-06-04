package com.controllers;

import com.domain.User;
import com.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/reg")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String getRegisterPage() {
        return "registration";
    }

    @PostMapping
    public String addUser(User user,
                          Map<String, Object> message) {

        boolean isUserAvailable = registrationService.isUserAvailable(user);
        if (isUserAvailable) {
            message.put("regStatusMessage", "User already exist");
            return "registration";
        }
        if (user.getUsername().isEmpty()
                || user.getUsername().trim().isEmpty()
                || user.getPassword().isEmpty()
                || user.getPassword().trim().isEmpty()) {
            message.put("regStatusMessage", "I see everything. Don't cheating!!!! Input a valid name mr. Masson");
            return "registration";
        }
        registrationService.createUser(user, message);
        return "registration";
    }


}
