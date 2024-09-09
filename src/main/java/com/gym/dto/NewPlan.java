package com.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewPlan {

    @NotNull
    private String nombre;

    @NotNull
    private BigDecimal costo;

    @NotNull
    private int duracion_dias;

    private String descripcion;

}
