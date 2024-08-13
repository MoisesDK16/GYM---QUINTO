package com.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "personal")
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CARGO")
    private Cargo cargo;

    @Column(name = "IDENTIFICACION", nullable = false, length = 15)
    private String identificacion;

    @Column(name = "TIPO_IDENTIFICACION", nullable = false, length = 4)
    private String tipoIdentificacion;

    @Column(name = "NOMBRE", nullable = false, length = 20)
    private String nombre;

    @Column(name = "PRIMER_APELLIDO", nullable = false, length = 20)
    private String primerApellido;

    @Column(name = "SEGUNDO_APELLIDO", length = 20)
    private String segundoApellido;

    @Column(name = "CORREO", length = 40)
    private String correo;

    @Column(name = "CLAVE", nullable = false, length = 100)
    private String clave;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "TELEFONO", length = 10)
    private String telefono;

    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

}
