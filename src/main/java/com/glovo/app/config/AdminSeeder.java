package com.glovo.app.config;

import com.glovo.app.entity.Rol;
import com.glovo.app.entity.Usuario;
import com.glovo.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminSeeder {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void crearAdminPorDefecto() {

        String adminEmail = "admin@glovo.com";

        if (usuarioRepository.existsByEmail(adminEmail)) {
            return; // ya existe, no hacemos nada
        }

        Usuario admin = Usuario.builder()
                .nombre("Administrador")
                .apellido("Sistema")
                .telefono("000000000")
                .email(adminEmail)
                .password(passwordEncoder.encode("admin123")) // ✅ contraseña: admin123
                .enabled(true)
                .rol(Rol.ADMIN) // 👈 AQUÍ ES ADMIN
                .build();

        usuarioRepository.save(admin);
        System.out.println("✔ Usuario ADMIN creado: " + adminEmail + " / admin123");
    }
}
