package com.gym.controllers;

import com.gym.models.Membresia;
import com.gym.services.MembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/membresias")
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    @PostMapping("/registrar")
    public ResponseEntity<Membresia> registrarMembresia(@RequestBody Membresia membresia){
        return ResponseEntity.ok(membresiaService.registrar(membresia));
    }
}
