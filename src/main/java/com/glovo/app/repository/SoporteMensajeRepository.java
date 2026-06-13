package com.glovo.app.repository;

import com.glovo.app.entity.SoporteMensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SoporteMensajeRepository extends JpaRepository<SoporteMensaje, Long> {

    // Para el scheduler
    List<SoporteMensaje> findByArchivadoFalseAndCreadoEnBefore(LocalDateTime fecha);
    // 🔹 Tickets de un usuario (por email)
    List<SoporteMensaje> findByEmailOrderByCreadoEnDesc(String email);

    // Para la vista admin
    List<SoporteMensaje> findByArchivadoFalseOrderByCreadoEnDesc();
    List<SoporteMensaje> findByArchivadoTrueOrderByCreadoEnDesc();

    
// Para el dashboard:
long countByArchivadoFalse();
long countByArchivadoTrue();
}
