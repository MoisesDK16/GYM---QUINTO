package com.gym.repositories;

import com.gym.models.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
}
