package com.security.Repository;

import com.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
    User findByRefreshToken(String refreshToken);
}