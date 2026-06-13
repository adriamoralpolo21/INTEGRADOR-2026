package com.glovo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponse {

    // Token de acceso (válido corto tiempo, ej. 1h)
    private String token;

    // Refresh token (válido más tiempo, ej. varios días)
    private String refreshToken;
}