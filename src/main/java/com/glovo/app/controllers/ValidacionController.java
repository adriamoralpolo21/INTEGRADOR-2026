// src/main/java/com/glovo/app/controllers/ValidacionController.java
package com.glovo.app.controllers;

import com.glovo.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/valid")
@RequiredArgsConstructor
public class ValidacionController {

    private final UsuarioRepository repo;

    @GetMapping("/email")
    public ResponseEntity<?> email(@RequestParam("v") String v) {
        String email = v.trim().toLowerCase();
        return repo.existsByEmail(email)
                ? ResponseEntity.status(409).body("Ese correo ya está registrado.")
                : ResponseEntity.ok().build();
    }

    @GetMapping("/phone")
    public ResponseEntity<?> phone(@RequestParam("v") String v) {
        String tel = v.replaceAll("\\D", "");
        return repo.existsByTelefono(tel)
                ? ResponseEntity.status(409).body("Ese número ya está registrado.")
                : ResponseEntity.ok().build();
    }
}
