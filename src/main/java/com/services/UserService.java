package com.services;

import com.domain.Role;
import com.domain.User;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void setDataForUserEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
    }

    public void editUserData(@RequestParam Map<String, String> form, @RequestParam("userId") User user) {
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
    }
}
