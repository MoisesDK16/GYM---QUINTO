package com.gym.controllers;

import com.gym.models.Producto;
import com.gym.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Producto>> findAll(int page, int size) {
        Page<Producto> productos= productoService.listarProductos(page, size);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id_producto}")
    public ResponseEntity<Producto> findById(@PathVariable String id_producto) {
        Producto producto = productoService.obtenerProducto(id_producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoService.guardarProducto(producto);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id_producto}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String id_producto, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id_producto,producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_producto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id_producto) {
        productoService.eliminarProducto(id_producto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
