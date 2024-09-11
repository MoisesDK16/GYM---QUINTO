package com.gym.controllers;

import com.gym.models.Membresia;
import com.gym.services.MembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/membresias")
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    @PostMapping("/registrar")
    public ResponseEntity<Membresia> registrar(@RequestBody Membresia membresia){
        return ResponseEntity.ok(membresiaService.registrar(membresia));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Membresia>> listar(
            @RequestParam int page,
            @RequestParam int size) {
        Page<Membresia> membresias = membresiaService.listar(page, size);
        return ResponseEntity.ok(membresias);
    }

    @GetMapping("uno/{id}")
    public ResponseEntity<Optional<Membresia>> uno(@PathVariable int id) {
        return ResponseEntity.ok(membresiaService.uno(id));
    }

    @GetMapping("/cliente/{id_cliente}")
    public ResponseEntity<List<Membresia>> listarPorCliente(@PathVariable String id_cliente) {
        return ResponseEntity.ok(membresiaService.listarPorCliente(id_cliente));
    }

    @GetMapping("cliente/apellido/{primer_apellido}")
    public ResponseEntity<List<Membresia>> listarPorClientePrimer_apellido(@PathVariable String primer_apellido) {
        return ResponseEntity.ok(membresiaService.listarPorClientePrimer_apellido(primer_apellido));
    }

    @GetMapping("/plan/{nombre}")
    public ResponseEntity<List<Membresia>> listarPorPlan(@PathVariable String nombre) {
        return ResponseEntity.ok(membresiaService.listarPorPlanNombre(nombre));
    }

    @GetMapping("/dias_restantes-asc")
    public ResponseEntity<List<Membresia>> listarPorDias_restantes() {
        return ResponseEntity.ok(membresiaService.listarPorDias_restantes());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Membresia>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(membresiaService.listarPorEstado(estado));
    }

}
