package com.glovo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SoporteApiResponse {

    private Long id;
    private String nombre;
    private String email;
    private String mensaje;
    private LocalDateTime creadoEn;
    private boolean archivado;
}
