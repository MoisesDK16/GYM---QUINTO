package com.gym.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle")
@ToString
public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "ID_MEMBRESIA")
    private Membresia membresia;

    @Column(name = "PRECIO", precision = 4, scale = 2)
    private BigDecimal precio;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "TOTAL", precision = 5, scale = 2)
    private BigDecimal total;
}
