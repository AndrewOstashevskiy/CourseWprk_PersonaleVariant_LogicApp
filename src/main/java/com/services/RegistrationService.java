package com.services;

import com.domain.Role;
import com.domain.User;
import com.domain.UserBalance;
import com.repositories.UserBalanceRepo;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserBalanceRepo userBalanceRepo;

    public void createUser(User user, Map<String, Object> message) {

        user.setActive(true);
        if (user.getUsername().equals("Admin")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }
        userRepository.save(user);

        UserBalance userBalance = UserBalance.builder()
                .userId(user)
                .balance(3)
                .build();
        userBalanceRepo.save(userBalance);
        message.put("regStatusMessage", "Registration success");
    }

    public boolean isUserAvailable(User user) {
        User userFromDb = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (userFromDb != null) {
            return true;
        }
         return false;
    }
}
