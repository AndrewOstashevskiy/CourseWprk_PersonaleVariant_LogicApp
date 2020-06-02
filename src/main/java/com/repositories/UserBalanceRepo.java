package com.repositories;

import com.domain.User;
import com.domain.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserBalanceRepo extends JpaRepository<UserBalance, UUID> {
    UserBalance findByUserId(User id);
}
