package com.glovo.app.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soporte_mensajes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SoporteMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false)
    private String nombre;

    @Column(length = 120, nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mensaje;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    // 🔹 Nuevo campo para saber si ya fue archivado
    @Builder.Default
@Column(name = "archivado", nullable = false)
private boolean archivado = false;

    @PrePersist
    void prePersist() {
        if (creadoEn == null) creadoEn = LocalDateTime.now();
    }
}
