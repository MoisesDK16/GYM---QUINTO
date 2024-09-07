package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membresia")

public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEMBRESIA")
    private Integer idMembresia;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_PLAN", nullable = false)
    private Plan plan;

    @Column(name = "FECHA_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "FECHA_FIN", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "DIAS_RESTANTES", length = 20)
    private Integer dias_restantes;

    @Column(name = "ESTADO", length = 20)
    private String estado;
}
