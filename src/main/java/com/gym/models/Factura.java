package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "factura")

public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FACTURA")
    private Integer idFactura;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private Personal usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @Column(name = "RUC", length = 15, nullable = false)
    private String ruc;

    @Column(name = "FECHA_EMISION", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "METODO_PAGO", length = 20, nullable = false)
    private String metodoPago;

    @Column(name = "SUBTOTAL", precision = 5, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(name = "IVA", precision = 3, scale = 2, nullable = false)
    private BigDecimal iva;

    @Column(name = "TOTAL", precision = 5, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "factura")
    List<Detalle> detalles;

}