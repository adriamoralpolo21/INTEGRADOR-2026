package com.glovo.app.dto;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
