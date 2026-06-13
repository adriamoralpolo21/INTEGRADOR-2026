package com.glovo.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @Size(max = 255)
    @Column(length = 255)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaProducto categoria;

    @Size(max = 255)
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean disponible = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean destacado = false;
}
