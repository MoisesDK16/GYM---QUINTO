package com.gym.controllers;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/generar")
    public ResponseEntity<String> generarFactura(@RequestBody Factura factura) {
        try {
            facturaService.generarFacturaProducto(
                    factura.getUsuario(),
                    factura.getCliente(),
                    factura.getRuc(),
                    factura.getFechaEmision(),
                    factura.getMetodoPago(),
                    factura.getSubtotal(),
                    factura.getIva(),
                    factura.getTotal()
            );
            return new ResponseEntity<>("Factura generada exitosamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
