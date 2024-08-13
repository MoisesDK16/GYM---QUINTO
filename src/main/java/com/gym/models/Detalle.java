package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle")

public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO", nullable = true)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA")
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "ID_MEMBRESIA", nullable = true)
    private Membresia membresia;

    @Column(name = "PRECIO", precision = 4, scale = 2)
    private BigDecimal precio;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "TOTAL", precision = 5, scale = 2)
    private BigDecimal total;
}
