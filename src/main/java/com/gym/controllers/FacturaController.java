package com.gym.controllers;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/all")
    public ResponseEntity<List<Factura>> getAll() {
        List<Factura> listaFacturas = facturaService.listarFacturas();
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = "application/pdf")
    public ModelAndView getAllAsPdf() {
        List<Factura> listaFacturas = facturaService.listarFacturas();
        Map<String, Object> model = new HashMap<>();
        model.put("factura", listaFacturas);
        return new ModelAndView(new FileController(facturaService), model);
    }


    @GetMapping("/last/{id_cliente}")
    public Optional<Factura> getLastFactura(@PathVariable String id_cliente) {
        return facturaService.findLastFactura(id_cliente);
    }
}
