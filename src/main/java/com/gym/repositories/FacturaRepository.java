package com.gym.repositories;

import com.gym.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    @Query("SELECT f FROM Factura f " +
            "WHERE f.cliente.id_cliente = :idCliente " +
            "ORDER BY f.fechaEmision DESC")

    Factura findLastFactura(@Param("idCliente") String idCliente);
}
