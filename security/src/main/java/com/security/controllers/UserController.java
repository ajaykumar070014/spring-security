package com.security.controllers;

import com.security.Repository.UserRepository;
import com.security.dto.RefreshTokenRequestDto;
import com.security.models.User;
import com.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.refresh.expiration}")
    private long refreshValidityInMilliseconds;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String accessToken = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        User dbUser = userRepository.findByUsername(user.getUsername());
        dbUser.setRefreshToken(refreshToken);
        dbUser.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + refreshValidityInMilliseconds));
        userRepository.save(dbUser);

        return "{ \"accessToken\": \"" + accessToken + "\", \"refreshToken\": \"" + refreshToken + "\" }";
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody RefreshTokenRequestDto refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken.getRefreshToken());
        if (user == null || user.getRefreshTokenExpiry().before(new Date())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateToken(user.getUsername());
        return "{ \"accessToken\": \"" + newAccessToken + "\" }";
    }

    @GetMapping("/user")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
