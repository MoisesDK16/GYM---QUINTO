package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")

public class Producto {

    @Id
    private String id_producto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private BigDecimal precio_compra;

    @Column(nullable = false)
    private BigDecimal margen_ganancia;

    @Column(nullable = false)
    private BigDecimal precio_venta;

    @Column
    private Date fecha_caducacion;

    @Column
    private String descripcion;

}
