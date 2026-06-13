package com.glovo.app.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UbicacionRequest {

    private String direccion;
    private Double latitud;
    private Double longitud;
}
