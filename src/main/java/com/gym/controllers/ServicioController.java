package com.gym.controllers;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import com.gym.services.ServicioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/servicios")
public class ServicioController {
    private final ServicioService service;

    @PostMapping("/crear")
    public ResponseEntity<Servicio> crearServicio(@RequestBody Servicio servicio) {
        Servicio servicioCreado = service.crearServicio(servicio);
        return new ResponseEntity<>(servicioCreado,HttpStatus.CREATED);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Void> actualizarServicio(@RequestBody Servicio servicio){
        service.actualizarServicio(servicio);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_servicio}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable int id_servicio) {
        service.eliminarServicio(id_servicio);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<Servicio>> listarServicios(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(service.listarServicios(page, size));
    }

    @GetMapping("uno/{id}")
    public ResponseEntity<Servicio> buscarServicioPorId(@PathVariable Integer id){
        Servicio servicio = service.obtenerServicio(id);
        return ResponseEntity.ok(servicio);
    }

    @GetMapping("/servicios-por-categoria")
    public ResponseEntity<Page<Servicio>> serviciosPorCategoria(
            @RequestPart Categoria categoria,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(service.ServiciosPorCategoria(categoria, page, size));
    }
}
