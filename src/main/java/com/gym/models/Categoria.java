package com.gym.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categoria")

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categoria;

    @Column
    private String categoria;
}
