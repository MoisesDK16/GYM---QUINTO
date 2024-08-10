package com.gym.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")

public class Cliente {

    @Id
    private String id_cliente;

    @Column
    private String tipo_identificacion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String primer_apellido;

    @Column
    private String segundo_apellido;

    @Column
    private String correo;

    @Column
    private String clave;

    @Column
    private String direccion;

    @Column
    private String telefono;

}
