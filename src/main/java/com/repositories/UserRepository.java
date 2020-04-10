package com.repositories;

import com.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDetails, UUID> {

    UserDetails findByUserName(String userName);
}
