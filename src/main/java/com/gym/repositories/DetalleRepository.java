package com.gym.repositories;

import com.gym.models.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
}
