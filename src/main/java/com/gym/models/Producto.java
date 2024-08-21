package com.gym.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne()
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonIgnore
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
