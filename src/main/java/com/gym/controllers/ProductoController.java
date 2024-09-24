package com.gym.controllers;

import com.gym.models.Categoria;
import com.gym.models.Producto;
import com.gym.repositories.CategoriaRepository;
import com.gym.services.ProductoService;
import com.gym.services.files.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepository;
    private final UploadFileService uploadFileService;

    @GetMapping("me/all")
    public ResponseEntity<Page<Producto>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Producto> productos = productoService.listar(page, size);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {
        Resource file = uploadFileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @GetMapping("me/{id_producto}")
    public ResponseEntity<Producto> findById(@PathVariable String id_producto) {
        Producto producto = productoService.obtenerProducto(id_producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }


    @PostMapping("/registrar")
    public ResponseEntity<Producto> guardarProducto(
            @RequestParam("idProducto") String idProducto,
            @RequestParam("categoriaId") Integer categoriaId,
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precioCompra") double precioCompra,
            @RequestParam("margenGanancia") double margenGanancia,
            @RequestParam("precioVenta") double precioVenta,
            @RequestParam("fecha_caducacion") Date fecha_caducacion,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Producto productoGuardado = productoService.registrar(idProducto, categoria, nombre, stock, precioCompra,
                margenGanancia, precioVenta, String.valueOf(fecha_caducacion), descripcion, imagen);

        return new ResponseEntity<>(productoGuardado, HttpStatus.OK);
    }

    @PatchMapping("/actualizar/{id_producto}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable String id_producto,
            @RequestParam("id_categoria") Integer categoriaId,
            @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock,
            @RequestParam("precioCompra") double precioCompra,
            @RequestParam("margenGanancia") double margenGanancia,
            @RequestParam("precioVenta") double precioVenta,
            @RequestParam("fecha_caducacion") Date fecha_caducacion,
            @RequestParam("descripcion") String descripcion) throws IOException {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Producto productoActualizado = productoService.actualizar(id_producto, nombre ,categoriaId, stock,
                precioCompra, margenGanancia, precioVenta, String.valueOf(fecha_caducacion), descripcion);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @PatchMapping("/actualizarImagen/{id_producto}")
    public ResponseEntity<Producto> actualizarImagen(@PathVariable String id_producto, @RequestParam("imagen") MultipartFile imagen) throws IOException {
        Producto producto = productoService.actualizarImagen(id_producto, imagen);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_producto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id_producto) {
        productoService.eliminar(id_producto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("me/actualizarStock/{id_producto}/{cantidad}")
    public ResponseEntity<String> actualizarStock(@PathVariable String id_producto , @PathVariable int cantidad) {
        try {
            productoService.actualizarStock(id_producto, cantidad);
            return ResponseEntity.ok("Stock actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //FILTROS

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> findByNombre(@PathVariable String nombre) {
        List<Producto> productos= productoService.buscarPorNombre(nombre);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/stock/{stock}")
    public ResponseEntity<List<Producto>> findByStock(@PathVariable Integer stock) {
        List<Producto> productos= productoService.buscarPorStock(stock);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> findByCategoriaId(@PathVariable int categoriaId) {
        List<Producto> productos= productoService.buscarPorCategoria(categoriaId);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/fecha_caducacion/{fecha_inicio}/{fecha_fin}")
    public ResponseEntity<List<Producto>> findByFecha_caducacion(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_fin) {
        List<Producto> productos = productoService.buscarPorFechaCaducacion(fecha_inicio, fecha_fin);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }



}
