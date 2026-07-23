package com.glovo.app.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // 👇 NUEVO: Atributo para controlar el stock de cada producto
    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Builder.Default
    @Column(nullable = false)
    private Integer stock = 0;
}