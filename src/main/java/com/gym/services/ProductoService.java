package com.gym.services;

import com.gym.models.Producto;
import com.gym.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.stream.Stream;
import java.util.Objects;

@Service
@Transactional

public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Page<Producto> listarProductos(int page, int size) {
        return productoRepository.findAll(PageRequest.of(page,size));
    }

    public Producto obtenerProducto(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id " + id + " no encontrado"));
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(String id_productoP, Producto producto) {

        if (id_productoP == null || id_productoP.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no debe ser nulo o vacío");
        }

        if (Stream.of(
                        producto.getCategoria(),
                        producto.getNombre(),
                        producto.getStock(),
                        producto.getPrecio_compra(),
                        producto.getMargen_ganancia(),
                        producto.getPrecio_venta(),
                        producto.getFecha_caducacion(),
                        producto.getDescripcion())
                .allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Debe completar los campos nulos");
        }

        Producto productoExistente = productoRepository.findById(id_productoP)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id_productoP + " no encontrado"));

        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setStock(producto.getStock());
        productoExistente.setPrecio_compra(producto.getPrecio_compra());
        productoExistente.setMargen_ganancia(producto.getMargen_ganancia());
        productoExistente.setPrecio_venta(producto.getPrecio_venta());
        productoExistente.setFecha_caducacion(producto.getFecha_caducacion());
        productoExistente.setDescripcion(producto.getDescripcion());

        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(String id) {
        productoRepository.deleteById(id);
    }

    //Excepciones Propias
    public static class ProductoNotFoundException extends RuntimeException {
        public ProductoNotFoundException(String message) {
            super(message);
        }
    }
}
