package com.glovo.app.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SoporteForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "Máximo 120 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 120, message = "Máximo 120 caracteres")
    private String email;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 255, message = "Máximo 255 caracteres")
    private String mensaje;
}
