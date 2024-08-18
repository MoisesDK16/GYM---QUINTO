package com.gym.controllers;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/generar")
    public ResponseEntity<Factura> generarFactura(@RequestBody Factura factura) {
        Factura facturaGenerada = facturaService.generarFactura(
                factura.getCliente(),
                factura.getRuc(),
                factura.getFechaEmision(),
                factura.getMetodoPago(),
                factura.getSubtotal(),
                factura.getIva(),
                factura.getTotal()
        );
        return new ResponseEntity<>(facturaGenerada, HttpStatus.CREATED);
    }


    @GetMapping("/last/{id_cliente}")
    public Optional<Factura> getLastFactura(@PathVariable String id_cliente) {
        return facturaService.findLastFactura(id_cliente);
    }
}
