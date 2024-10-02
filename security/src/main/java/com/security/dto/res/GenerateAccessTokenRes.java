package com.security.dto.res;

import lombok.Data;
@Data
public class GenerateAccessTokenRes {
    private Data data;
    @lombok.Data
    public static class Data{
        private String accessToken;
    }
}
