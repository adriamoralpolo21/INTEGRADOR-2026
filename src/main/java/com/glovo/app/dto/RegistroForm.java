package com.glovo.app.dto;

import com.glovo.app.validation.UniqueEmail;
import com.glovo.app.validation.UniquePhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegistroForm {

    @NotBlank(message = "Coloca tu nombre.")
    @Size(min = 2, max = 60, message = "Tu nombre debe tener entre 2 y 60 caracteres.")
    private String nombre;

    @NotBlank(message = "Coloca tu apellido.")
    @Size(min = 2, max = 60, message = "Tu apellido debe tener entre 2 y 60 caracteres.")
    private String apellido;

    @NotBlank(message = "Ingresa tu número de celular.")
    @Pattern(regexp = "\\d{9}", message = "El número debe tener 9 dígitos.")
    @UniquePhone(message = "Ese número ya está registrado.")
    private String telefono;

    @NotBlank(message = "Ingresa tu correo.")
    @Email(message = "El correo debe ser válido (incluye “@”).")
    @UniqueEmail(message = "Ese correo ya está registrado.")
    private String email;

    @NotBlank(message = "Crea una contraseña segura.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    @NotBlank(message = "Confirma tu contraseña.")
    private String confirmPassword;
}
