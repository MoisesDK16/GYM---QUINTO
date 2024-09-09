package com.gym.controllers;

import com.gym.models.Membresia;
import com.gym.services.MembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
