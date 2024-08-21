package com.gym.repositories;

import com.gym.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    @Query(value = "SELECT * FROM Factura f " +
            "WHERE f.ID_CLIENTE = :idCliente " +
            "ORDER BY f.FECHA_EMISION DESC LIMIT 1", nativeQuery = true)
    Optional<Factura> findLastFactura(String idCliente);

}
