package com.gym.repositories;

import com.gym.models.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {

    @Query("SELECT m FROM Membresia m WHERE m.cliente.id_cliente = :id_cliente")
    List<Membresia> findMembresiasByClienteId_cliente(@Param("id_cliente") String id_cliente);

    @Query("SELECT m FROM Membresia m WHERE m.cliente.primer_apellido = :primer_apellido")
    List<Membresia> findByClientePrimer_apellido(@Param("primer_apellido") String primer_apellido);

    @Query("SELECT m FROM Membresia m WHERE m.plan.nombre = :nombre")
    List<Membresia> findMembresiaByPlanNombre(@Param("nombre") String nombre);

    @Query("SELECT m FROM Membresia m ORDER BY m.dias_restantes ASC")
    List<Membresia> findMembresiasByOrderByDias_restantesAsc();

    @Query("SELECT m FROM Membresia m WHERE m.estado = :estado")
    List<Membresia> findMembresiasByEstado(@Param("estado") String estado);
}
