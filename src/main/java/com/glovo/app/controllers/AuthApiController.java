package com.glovo.app.controllers;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.glovo.app.dto.JwtAuthRequest;
import com.glovo.app.dto.JwtAuthResponse;
import com.glovo.app.dto.JwtRefreshRequest;
import com.glovo.app.dto.ChangePasswordRequest;
import com.glovo.app.services.UsuarioService;
import com.glovo.app.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;   // 👈 nuevo

    // ========= LOGIN: devuelve access + refresh =========
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest request) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtAuthResponse(accessToken, refreshToken));
    }

    // ========= REFRESH: recibe refreshToken, devuelve nuevo accessToken =========
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody JwtRefreshRequest request) {

        String refreshToken = request.getRefreshToken();

        // 1) Validar que el refresh token sea válido y no esté vencido
        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token inválido o expirado");
        }

        // 2) Sacar el email del refresh token
        String email = jwtUtil.extractUsername(refreshToken);

        // 3) Cargar los datos del usuario
        var userDetails = userDetailsService.loadUserByUsername(email);

        // 4) Generar un nuevo access token
        String newAccessToken = jwtUtil.generateToken(userDetails);

        // 5) Respondemos con el nuevo access token (podemos reutilizar el mismo refresh)
        return ResponseEntity.ok(new JwtAuthResponse(newAccessToken, refreshToken));
    }

    // ========= CAMBIO DE CONTRASEÑA (USUARIO AUTENTICADO) =========
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,
                                            Authentication auth) {

        String email = auth.getName(); // viene del JWT

        try {
            usuarioService.cambiarPassword(email, request);
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } catch (IllegalArgumentException ex) {
            // errores de validación (contraseña actual incorrecta, no coinciden, etc.)
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
