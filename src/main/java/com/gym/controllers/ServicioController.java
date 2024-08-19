package com.gym.controllers;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import com.gym.services.ServicioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("api/servicios")
public class ServicioController {
    private final ServicioService service;

@PostMapping("/crear")
    public ResponseEntity<String> crearServicio(
            @RequestPart String nombre,
            @RequestPart String categoria,
            @RequestPart BigDecimal precio){
        service.crearServicio(nombre, categoria, precio);
        return ResponseEntity.ok("Servicio creado");
    }

    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizarServicio(
            @RequestPart Long id,
            @RequestPart String nombre,
            @RequestPart String categoria,
            @RequestPart BigDecimal precio){
        service.actualizarServicio(id, nombre, categoria, precio);
        return ResponseEntity.ok("Servicio actualizado");
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarServicio(@RequestPart Long id){
        service.eliminarServicio(id);
        return ResponseEntity.ok("Servicio eliminado");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Servicio>> listarServicios(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(service.listarServicios(page, size));
    }

    @GetMapping("/servicios-por-categoria")
    public ResponseEntity<Page<Servicio>> serviciosPorCategoria(
            @RequestPart Categoria categoria,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(service.ServiciosPorCategoria(categoria, page, size));
    }
}
