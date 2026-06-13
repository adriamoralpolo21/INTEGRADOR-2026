package com.glovo.app.services;

import com.glovo.app.dto.SoporteForm;
import com.glovo.app.entity.SoporteMensaje;
import com.glovo.app.repository.SoporteMensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoporteService {

    private final SoporteMensajeRepository soporteMensajeRepository;

    @Transactional
    public SoporteMensaje crearTicket(SoporteForm form) {
        SoporteMensaje m = SoporteMensaje.builder()
                .nombre(form.getNombre())
                .email(form.getEmail())
                .mensaje(form.getMensaje())
                .archivado(false)
                .build();
        return soporteMensajeRepository.save(m);
    }

    // 🔁 Usado por el scheduler
    @Transactional
    public void archivarMensajesAntiguos() {
        LocalDateTime limite = LocalDateTime.now().minusDays(7); // cuando termines de probar

        var antiguos = soporteMensajeRepository
                .findByArchivadoFalseAndCreadoEnBefore(limite);

        antiguos.forEach(m -> m.setArchivado(true));
    }

    // 🔹 Para el panel admin
    @Transactional(readOnly = true)
    public List<SoporteMensaje> listarActivos() {
        return soporteMensajeRepository.findByArchivadoFalseOrderByCreadoEnDesc();
    }

    @Transactional(readOnly = true)
    public List<SoporteMensaje> listarArchivados() {
        return soporteMensajeRepository.findByArchivadoTrueOrderByCreadoEnDesc();
    }

    @Transactional
    public void archivarPorId(Long id) {
        soporteMensajeRepository.findById(id).ifPresent(m -> m.setArchivado(true));
    }

    @Transactional
    public void restaurarPorId(Long id) {
        soporteMensajeRepository.findById(id).ifPresent(m -> m.setArchivado(false));
    }

    @Transactional
    public void eliminarPorId(Long id) {
        soporteMensajeRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
public long contarTicketsActivos() {
    return soporteMensajeRepository.countByArchivadoFalse();
}

@Transactional(readOnly = true)
public long contarTicketsArchivados() {
    return soporteMensajeRepository.countByArchivadoTrue();
}
}
