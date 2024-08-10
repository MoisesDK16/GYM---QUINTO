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

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @Column
    private String nombre;

    @Column
    private int stock;

    @Column
    private BigDecimal precio_compra;

    @Column
    private BigDecimal margen_ganancia;

    @Column
    private BigDecimal precio_venta;

    @Column
    private Date fecha_caducacion;

    @Column
    private String descripcion;

}
