package com.gym.services;

import com.gym.models.Categoria;
import com.gym.models.Producto;
import com.gym.repositories.CategoriaRepository;
import com.gym.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.Objects;

@Service
@Transactional
@Log4j2

public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Producto> listar(int page, int size) {
        return productoRepository.findAll(PageRequest.of(page,size));
    }

    public Producto obtenerProducto(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id " + id + " no encontrado"));
    }

    public Producto registrar(Producto producto) {
        if (producto.getCategoria() == null || producto.getCategoria().getId_categoria()==0) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        // Verifica si la categoría ya existe en la base de datos
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(producto.getCategoria().getId_categoria());

        if (!categoriaExistente.isPresent()) {
            throw new IllegalArgumentException("La categoría proporcionada no existe en la base de datos");
        }

        return productoRepository.save(producto);
    }

    public Producto actualizar(String id_producto, Producto producto) {

        if (id_producto == null || id_producto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no debe ser nulo o vacío");
        }

        if (Stream.of(
                        producto.getCategoria(),
                        producto.getNombre(),
                        producto.getStock(),
                        producto.getPrecio_compra(),
                        producto.getMargen_ganancia(),
                        producto.getPrecio_venta())
                .allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Debe completar los campos no nulos");
        }

        Producto productoExistente = productoRepository.findById(id_producto)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id_producto + " no encontrado"));

        // Asegúrate de que la categoría está gestionada por el contexto de persistencia
        Categoria categoria = producto.getCategoria();
        if (categoria != null && categoria.getId_categoria() != 0) {
            categoria = categoriaRepository.findById(categoria.getId_categoria())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            productoExistente.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        productoExistente.setNombre(producto.getNombre());
        productoExistente.setStock(producto.getStock());
        productoExistente.setPrecio_compra(producto.getPrecio_compra());
        productoExistente.setMargen_ganancia(producto.getMargen_ganancia());
        productoExistente.setPrecio_venta(producto.getPrecio_venta());
        productoExistente.setFecha_caducacion(producto.getFecha_caducacion());
        productoExistente.setDescripcion(producto.getDescripcion());

        return productoRepository.save(productoExistente);
    }

    public void eliminar(String id) {
        productoRepository.deleteById(id);
    }

    //Excepciones Propias
    public static class ProductoNotFoundException extends RuntimeException {
        public ProductoNotFoundException(String message) {
            super(message);
        }
    }
}
