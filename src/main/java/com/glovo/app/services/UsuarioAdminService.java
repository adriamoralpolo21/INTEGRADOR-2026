package com.glovo.app.services;

import com.glovo.app.entity.Rol;
import com.glovo.app.entity.Usuario;
import com.glovo.app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioAdminService {

    private final UsuarioRepository usuarioRepository;

    // 🔹 Estadísticas para el dashboard
    @Transactional(readOnly = true)
    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    @Transactional(readOnly = true)
    public long contarAdmins() {
        return usuarioRepository.countByRol(Rol.ADMIN);
    }

    @Transactional(readOnly = true)
    public long contarUsers() {
        return usuarioRepository.countByRol(Rol.USER);
    }

    @Transactional(readOnly = true)
    public long contarActivos() {
        return usuarioRepository.countByEnabledTrue();
    }

    @Transactional(readOnly = true)
    public long contarBloqueados() {
        return usuarioRepository.countByEnabledFalse();
    }

    // 🔹 Listado de usuarios
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllByOrderByIdAsc();
    }

    // 🔹 Cambiar rol USER / ADMIN
    @Transactional
    public void cambiarRol(Long id, Rol nuevoRol) {
        usuarioRepository.findById(id).ifPresent(u -> u.setRol(nuevoRol));
    }

    // 🔹 Bloquear / desbloquear
    @Transactional
    public void cambiarEstado(Long id, boolean enabled) {
        usuarioRepository.findById(id).ifPresent(u -> u.setEnabled(enabled));
    }
}
