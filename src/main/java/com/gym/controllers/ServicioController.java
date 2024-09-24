package com.gym.controllers;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import com.gym.services.ServicioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/actualizar/{id_servicio}")
    public ResponseEntity<Void> actualizarServicio(@PathVariable Integer id_servicio, @RequestBody Servicio servicio){
        service.actualizarServicio(id_servicio,servicio);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_servicio}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable int id_servicio) {
        service.eliminarServicio(id_servicio);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("me/all")
    public ResponseEntity<List<Servicio>> listarServicios(){
        return ResponseEntity.ok(service.listarServicios());
    }

    @GetMapping("me/uno/{id}")
    public ResponseEntity<Optional<Servicio>> buscarServicioPorId(@PathVariable Integer id){
        Optional<Servicio> servicio = service.obtenerServicio(id);
        return ResponseEntity.ok(servicio);
    }

    @GetMapping("me/servicios-por-categoria")
    public ResponseEntity<List<Servicio>> serviciosPorCategoria(
            @RequestBody Categoria categoria){
        return ResponseEntity.ok(service.ServiciosPorCategoria(categoria));
    }
}
