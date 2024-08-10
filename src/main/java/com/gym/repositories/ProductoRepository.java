package com.gym.repositories;

import com.gym.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    @Query("SELECT p FROM Producto p JOIN p.categoria c")
    Page<Producto> findAll(Pageable pageable);
}
