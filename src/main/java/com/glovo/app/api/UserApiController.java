package com.glovo.app.api;

import com.glovo.app.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth") // para Swagger, requiere JWT
public class UserApiController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/me")
    public UserResponse me(Authentication auth) {

        String email = auth.getName(); // viene del JWT (subject)

        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        return new UserResponse(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol().name()
        );
    }

    public record UserResponse(
            String nombre,
            String apellido,
            String email,
            String telefono,
            String rol
    ) {}
}
