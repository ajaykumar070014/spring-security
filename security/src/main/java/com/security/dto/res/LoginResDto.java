package com.security.dto.res;

import lombok.Data;

import java.util.List;

@Data
public class LoginResDto {
    private Data data;
    @lombok.Data
    public static class Data{
        private String accessToken;
        private String refreshToken;
    }
}
