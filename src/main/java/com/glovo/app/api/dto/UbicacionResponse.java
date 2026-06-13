package com.glovo.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UbicacionResponse {

    private String direccion;
    private Double latitud;
    private Double longitud;
}
