package com.glovo.app.services;

import com.glovo.app.dto.ChangePasswordRequest;
import com.glovo.app.dto.RegistroForm;
import com.glovo.app.entity.Rol;
import com.glovo.app.entity.Usuario;
import com.glovo.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registrar(RegistroForm f) {
        if (!f.getPassword().equals(f.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        String email = f.getEmail().trim().toLowerCase();
        String telefono = f.getTelefono().replaceAll("\\D", "");

        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        if (usuarioRepository.existsByTelefono(telefono)) {
            throw new IllegalArgumentException("Ese número ya está registrado.");
        }

        Usuario u = Usuario.builder()
                .nombre(f.getNombre())
                .apellido(f.getApellido())
                .telefono(telefono)
                .email(email)
                .password(passwordEncoder.encode(f.getPassword()))
                .enabled(true)
                .rol(Rol.USER)          // 👈 AQUÍ LE DAS ROL USER POR DEFECTO
                .build();

        try {
            usuarioRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("uk_usuarios_email") || msg.contains("email"))
                throw new IllegalArgumentException("El correo ya está registrado.");
            if (msg.contains("uk_usuarios_telefono") || msg.contains("telefono"))
                throw new IllegalArgumentException("Ese número ya está registrado.");
            throw e;
        }
    }
    @Transactional
public void cambiarPassword(String email, ChangePasswordRequest req) {

    var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    if (!passwordEncoder.matches(req.getCurrentPassword(), usuario.getPassword())) {
        throw new IllegalArgumentException("La contraseña actual no es correcta.");
    }

    if (!req.getNewPassword().equals(req.getConfirmPassword())) {
        throw new IllegalArgumentException("Las contraseñas nuevas no coinciden.");
    }

    if (req.getNewPassword().length() < 6) {
        throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres.");
    }

    usuario.setPassword(passwordEncoder.encode(req.getNewPassword()));
    usuarioRepository.save(usuario);
}
public void actualizarUbicacionEntrega(String email, String direccion, Double latitud, Double longitud) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) return;

        Usuario u = opt.get();
        u.setDireccionEntrega(direccion);
        u.setLatitudEntrega(latitud);
        u.setLongitudEntrega(longitud);
        usuarioRepository.save(u);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}