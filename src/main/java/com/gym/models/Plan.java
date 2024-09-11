package com.gym.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_plan;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal costo;

    @Column(nullable = false)
    private Integer duracion_dias;

    @Column
    private String descripcion;

    @Column
    private String imagen;

    @ManyToMany
    @JoinTable(
            name = "servicio_x_plan", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_plan"), // Clave foránea de la tabla "plan"
            inverseJoinColumns = @JoinColumn(name = "id_servicio") // Clave foránea de la tabla "servicio"
    )
    private List<Servicio> servicios;
}
