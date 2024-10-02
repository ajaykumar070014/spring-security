package com.security.controllers;

import com.security.Repository.UserRepository;
import com.security.dto.req.RefreshTokenRequestDto;
import com.security.dto.req.RegisterReqDto;
import com.security.dto.res.GenerateAccessTokenRes;
import com.security.dto.res.LoginResDto;
import com.security.dto.res.UserResDto;
import com.security.models.User;
import com.security.services.UserService;
import com.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public UserResDto register(@RequestBody RegisterReqDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResDto login(@RequestBody RegisterReqDto user) {
        return userService.login(user);
    }

    @PostMapping("/refresh")
    public GenerateAccessTokenRes refresh(@RequestBody RefreshTokenRequestDto refreshToken) {
        return userService.generateAccessToken(refreshToken);
    }

    @GetMapping("/user")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
