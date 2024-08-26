package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")

public class Producto {

    @Id
    @Column(name = "idProducto")
    private String idProducto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoriaId", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private BigDecimal precioCompra;

    @Column(nullable = false)
    private BigDecimal margenGanancia;

    @Column(nullable = false)
    private BigDecimal precioVenta;

    @Column
    private LocalDate fecha_caducacion;

    @Column
    private String descripcion;

    @Column
    private String imagen;

}
