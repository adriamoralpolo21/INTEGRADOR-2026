package com.glovo.app.api;

import com.glovo.app.dto.SoporteApiResponse;
import com.glovo.app.entity.SoporteMensaje;
import com.glovo.app.repository.SoporteMensajeRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin/soporte")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth") // también requiere JWT
public class AdminSoporteApiController {

    private final SoporteMensajeRepository soporteMensajeRepository;

    @GetMapping
    public List<SoporteApiResponse> listarTodos() {
        return soporteMensajeRepository.findAll().stream()
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

    @PatchMapping("/{id}/archivar")
    public void archivar(@PathVariable Long id) {
        SoporteMensaje m = soporteMensajeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        m.setArchivado(true);
        soporteMensajeRepository.save(m);
    }

    @PatchMapping("/{id}/restaurar")
    public void restaurar(@PathVariable Long id) {
        SoporteMensaje m = soporteMensajeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        m.setArchivado(false);
        soporteMensajeRepository.save(m);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        if (!soporteMensajeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        soporteMensajeRepository.deleteById(id);
    }
}
