package com.gym.repositories;

import com.gym.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    @Query(value = "SELECT * FROM Factura f " +
            "WHERE f.ID_CLIENTE = :idCliente " +
            "ORDER BY f.FECHA_EMISION DESC LIMIT 1", nativeQuery = true)
    Optional<Factura> findLastFactura(String idCliente);

    @Query("SELECT f FROM Factura f JOIN f.detalles d WHERE d.producto IS NOT NULL")
    List<Factura> findFacturasWithProductos();

    @Query("SELECT f FROM Factura f " +
            "JOIN f.detalles d " +
            "WHERE FUNCTION('DATE', f.fechaEmision) BETWEEN FUNCTION('DATE', :fechaInicio) AND FUNCTION('DATE', :fechaFin) " +
            "AND f.metodoPago = :metodoPago AND d.producto IS NOT NULL")
    List<Factura> findFacturasProductosByFecha(String fechaInicio, String fechaFin, String metodoPago);

    @Query("SELECT f FROM Factura f " +
            "JOIN f.detalles d " +
            "WHERE FUNCTION('DATE', f.fechaEmision) BETWEEN FUNCTION('DATE', :fechaInicio) AND FUNCTION('DATE', :fechaFin) " +
            "AND d.producto IS NOT NULL")
    List<Factura> findFacturasProductosByFecha(String fechaInicio, String fechaFin);


    @Query("SELECT f FROM Factura f JOIN f.detalles d " +
            "WHERE d.producto IS NOT NULL AND f.cliente.id_cliente LIKE %:idCliente%")
    List<Factura> findFacturasProductosByCliente(String idCliente);

    @Query("SELECT f FROM Factura f JOIN f.detalles d " +
            "WHERE d.producto IS NOT NULL " +
            "AND CONCAT(f.cliente.nombre,' ', f.cliente.primer_apellido) LIKE %:nombreApellido%")
    List<Factura> findFacturasByNombreCompleto(@Param("nombreApellido") String nombreApellido);

}
