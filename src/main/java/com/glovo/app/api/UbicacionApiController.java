package com.glovo.app.api;

import com.glovo.app.api.dto.UbicacionRequest;
import com.glovo.app.api.dto.UbicacionResponse;
import com.glovo.app.entity.Usuario;
import com.glovo.app.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/ubicacion")
@RequiredArgsConstructor
public class UbicacionApiController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> guardarUbicacion(@RequestBody UbicacionRequest req,
                                                 Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        usuarioService.actualizarUbicacionEntrega(
                principal.getName(),
                req.getDireccion(),
                req.getLatitud(),
                req.getLongitud()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UbicacionResponse> obtenerUbicacion(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Usuario> opt = usuarioService.obtenerPorEmail(principal.getName());
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario u = opt.get();
        return ResponseEntity.ok(
                new UbicacionResponse(
                        u.getDireccionEntrega(),
                        u.getLatitudEntrega(),
                        u.getLongitudEntrega()
                )
        );
    }
}
