package com.glovo.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.glovo.app.entity.Rol;
import lombok.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_usuarios_telefono", columnNames = "telefono")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 60)
    @Column(nullable = false, length = 60)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 60)
    @Column(nullable = false, length = 60)
    private String apellido;

    @NotBlank
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    @Column(nullable = false, unique = true, length = 9)
    private String telefono;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;

    /** Normaliza antes de guardar/actualizar */
    @PrePersist
    @PreUpdate
    private void normalize() {
        if (email != null)
            email = email.trim().toLowerCase();
        if (telefono != null)
            telefono = telefono.replaceAll("\\D", "");
    }

@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 20)
@Builder.Default
private Rol rol = Rol.USER;

// Dirección de entrega preferida
@Column(length = 255)
private String direccionEntrega;

// Coordenadas guardadas (opcional)
private Double latitudEntrega;
private Double longitudEntrega;

}
