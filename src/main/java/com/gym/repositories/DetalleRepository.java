package com.gym.repositories;

import com.gym.models.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {

    Detalle getByIdDetalle(int idDetalle);
}
