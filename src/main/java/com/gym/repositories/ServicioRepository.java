package com.gym.repositories;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Page<Servicio> findAllByCategoria(Categoria categoria, Pageable pageable);
    Servicio findByNombreContainingIgnoreCase(String nombre);

}
