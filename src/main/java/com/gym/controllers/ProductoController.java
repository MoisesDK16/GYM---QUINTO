package com.gym.controllers;

import com.gym.models.Producto;
import com.gym.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Producto>> findAll(int page, int size) {
        Page<Producto> productos= productoService.listar(page, size);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id_producto}")
    public ResponseEntity<Producto> findById(@PathVariable String id_producto) {
        Producto producto = productoService.obtenerProducto(id_producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoService.registrar(producto);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id_producto}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String id_producto, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizar(id_producto,producto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_producto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id_producto) {
        productoService.eliminar(id_producto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/actualizarStock/{id_producto}/{cantidad}")
    public ResponseEntity<String> actualizarStock(@PathVariable String id_producto , @PathVariable int cantidad) {
        try {
            productoService.actualizarStock(id_producto, cantidad);
            return ResponseEntity.ok("Stock actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
