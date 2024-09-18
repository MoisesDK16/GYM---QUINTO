package com.gym.repositories;

import com.gym.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    Page<Producto> findAll(Pageable pageable);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> findByNombre(String nombre);

    @Query("SELECT p FROM Producto p WHERE p.stock = :stock OR p.stock < :stock")
    List<Producto> findByStock(Integer stock);

    @Query("SELECT p FROM Producto p WHERE p.categoria.id_categoria = :categoriaId")
    List<Producto> findByCategoriaId(int categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.fecha_caducacion BETWEEN :fecha_inicio AND :fecha_fin")
    List<Producto> findByFecha_caducacion(LocalDate fecha_inicio, LocalDate fecha_fin);
}
