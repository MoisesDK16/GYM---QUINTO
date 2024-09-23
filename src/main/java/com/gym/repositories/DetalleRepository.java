package com.gym.repositories;

import com.gym.models.Detalle;
import com.gym.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {

    Detalle getByIdDetalle(int idDetalle);

    @Query("SELECT d FROM Detalle d JOIN d.factura f WHERE d.membresia IS NOT NULL")
    List<Detalle> findFacturasWithMembresia();

    @Query("SELECT d FROM Detalle d JOIN d.factura f " +
            "WHERE FUNCTION('DATE', f.fechaEmision) BETWEEN FUNCTION('DATE', :fechaInicio) AND FUNCTION('DATE', :fechaFin) " +
            "AND f.metodoPago = :metodoPago AND d.membresia IS NOT NULL")
    List<Detalle> findFacturasMembresiasByFecha(String fechaInicio, String fechaFin, String metodoPago);

    @Query("SELECT d FROM Detalle d JOIN d.factura f " +
            "WHERE FUNCTION('DATE', f.fechaEmision) BETWEEN FUNCTION('DATE', :fechaInicio) AND FUNCTION('DATE', :fechaFin) " +
            "AND d.membresia IS NOT NULL")
    List<Detalle> findFacturasMembresiasByFecha(String fechaInicio, String fechaFin);


    @Query("SELECT d FROM Detalle d JOIN d.factura f " +
            "WHERE d.membresia IS NOT NULL AND f.cliente.id_cliente LIKE %:idCliente%")
    List<Detalle> findFacturasMembresiasByCliente(String idCliente);

    @Query("SELECT d FROM Detalle d JOIN d.factura f " +
            "WHERE concat(f.cliente.nombre,' ',f.cliente.primer_apellido) LIKE %:nombreApellido% AND d.membresia IS NOT NULL")
    List<Detalle> findFacturasByNombreCompleto(@Param("nombreApellido") String nombreApellido);

}
