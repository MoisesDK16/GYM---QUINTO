package com.gym.repositories;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findAllByCategoria(Categoria categoria);
    Servicio findByNombreContainingIgnoreCase(String nombre);
}
