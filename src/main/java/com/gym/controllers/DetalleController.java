package com.gym.controllers;

import com.gym.models.Detalle;
import com.gym.services.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("listar/{idFactura}")
    public ResponseEntity<List<Detalle>> listarDetallesFactura(@PathVariable Integer idFactura){
        List<Detalle> listaDetalles = detalleService.listarDetallesFactura(idFactura);
        return new ResponseEntity<>(listaDetalles,HttpStatus.OK);
    }

    @GetMapping("unoDetalle/{idDetalle}")
    public ResponseEntity<Detalle> unoDetalle(@PathVariable Integer idDetalle){
        Detalle detalle = detalleService.unoDetalle(idDetalle);
        return new ResponseEntity<>(detalle,HttpStatus.OK);
    }

}
