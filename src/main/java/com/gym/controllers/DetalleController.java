package com.gym.controllers;

import com.gym.models.Detalle;
import com.gym.repositories.DetalleRepository;
import com.gym.services.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/detalles")
public class DetalleController {
    @Autowired
    private DetalleService detalleService;

    @PostMapping("/generar")
    public ResponseEntity<Detalle> generar(@RequestBody Detalle detalle){
        try{
            Detalle detalleCreado = detalleService.guardarDetalleProducto(detalle);
            return new ResponseEntity<>(detalleCreado ,HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
