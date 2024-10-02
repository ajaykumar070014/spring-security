package com.security.mapper;

import com.security.dto.req.RegisterReqDto;
import com.security.dto.res.GenerateAccessTokenRes;
import com.security.dto.res.LoginResDto;
import com.security.dto.res.UserResDto;
import com.security.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Converter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User coverToEntity(RegisterReqDto registerReqDto) {
        User user = new User();
        user.setUsername(registerReqDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerReqDto.getPassword()));

        return user;
    }

    public UserResDto covertToDto(User user) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setUsername(user.getUsername());

        return userResDto;
    }

    public LoginResDto convertToLoginDto(User user, String accessToken) {
        LoginResDto.Data data = new LoginResDto.Data();
        data.setAccessToken(accessToken);
        data.setRefreshToken(user.getRefreshToken());

        LoginResDto loginResDto = new LoginResDto();
        loginResDto.setData(data);

        return loginResDto;
    }

    public GenerateAccessTokenRes convertToAccessTokenResDTo(String accessToken) {
        GenerateAccessTokenRes.Data data = new GenerateAccessTokenRes.Data();
        data.setAccessToken(accessToken);

        GenerateAccessTokenRes generateAccessTokenRes = new GenerateAccessTokenRes();
        generateAccessTokenRes.setData(data);

        return generateAccessTokenRes;
    }
}
