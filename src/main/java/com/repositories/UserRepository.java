package com.repositories;

import com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    List<User> findAllByUsernameIsNotAndPasswordIsNot(String username, String password);
}
