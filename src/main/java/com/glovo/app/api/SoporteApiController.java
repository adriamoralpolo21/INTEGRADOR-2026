package com.glovo.app.api;

import com.glovo.app.dto.SoporteApiRequest;
import com.glovo.app.dto.SoporteApiResponse;
import com.glovo.app.entity.SoporteMensaje;
import com.glovo.app.repository.SoporteMensajeRepository;
import com.glovo.app.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/soporte")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth") // Swagger → requiere JWT
public class SoporteApiController {

    private final SoporteMensajeRepository soporteMensajeRepository;
    private final UsuarioRepository usuarioRepository;

    // Crear ticket de soporte (usuario logueado)
    @PostMapping
    public SoporteApiResponse crearTicket(@RequestBody SoporteApiRequest request,
                                          Authentication auth) {

        String email = auth.getName(); // viene del JWT

        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado")
                );

        SoporteMensaje mensaje = SoporteMensaje.builder()
                .nombre(usuario.getNombre() + " " + usuario.getApellido())
                .email(usuario.getEmail())
                .mensaje(request.getMensaje())
                .archivado(false)
                .build();

        mensaje = soporteMensajeRepository.save(mensaje);

        return new SoporteApiResponse(
                mensaje.getId(),
                mensaje.getNombre(),
                mensaje.getEmail(),
                mensaje.getMensaje(),
                mensaje.getCreadoEn(),
                mensaje.isArchivado()
        );
    }

    // Ver mis tickets (activos y archivados)
    @GetMapping("/mis")
    public List<SoporteApiResponse> misTickets(Authentication auth) {

        String email = auth.getName();

        return soporteMensajeRepository.findByEmailOrderByCreadoEnDesc(email)
                .stream()
                .map(m -> new SoporteApiResponse(
                        m.getId(),
                        m.getNombre(),
                        m.getEmail(),
                        m.getMensaje(),
                        m.getCreadoEn(),
                        m.isArchivado()
                ))
                .toList();
    }
}
