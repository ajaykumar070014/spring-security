package com.security.services;

import com.security.Repository.UserRepository;
import com.security.dto.req.RefreshTokenRequestDto;
import com.security.dto.req.RegisterReqDto;
import com.security.dto.res.GenerateAccessTokenRes;
import com.security.dto.res.LoginResDto;
import com.security.dto.res.UserResDto;
import com.security.mapper.Converter;
import com.security.models.User;
import com.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Converter converter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.refresh.expiration}")
    private long refreshValidityInMilliseconds;

    public UserResDto registerUser(RegisterReqDto registerReqDto) {
        User user = converter.coverToEntity(registerReqDto);
        userRepository.save(user);
        return converter.covertToDto(user);
    }

    public LoginResDto login(RegisterReqDto registerReqDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerReqDto.getUsername(), registerReqDto.getPassword()));
        String accessToken = jwtUtil.generateToken(registerReqDto.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(registerReqDto.getUsername());
        User dbUser = userRepository.findByUsername(registerReqDto.getUsername());
        dbUser.setRefreshToken(refreshToken);
        dbUser.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + refreshValidityInMilliseconds));
        userRepository.save(dbUser);
        return converter.convertToLoginDto(dbUser, accessToken);
    }


    public GenerateAccessTokenRes generateAccessToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        User user = userRepository.findByRefreshToken(refreshTokenRequestDto.getRefreshToken());
        if (user == null || user.getRefreshTokenExpiry().before(new Date())) {
            throw new RuntimeException("Invalid refresh token");
        }
        String accessToken = jwtUtil.generateToken(user.getUsername());
        return converter.convertToAccessTokenResDTo(accessToken);
    }
}
