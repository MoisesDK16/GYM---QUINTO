package com.gym.controllers;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import com.gym.services.files.MembresiaFactura;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.services.files.ListarDetallesFactura;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    private final ListarDetallesFactura listarDetallesFactura;
    private final MembresiaFactura membresiaFactura;

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

    @GetMapping(value = "/DownloadPdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadPDF(@PathVariable Integer id, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_" + id + ".pdf");

            listarDetallesFactura.buildPdfDocument(id, response);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("Error al generar el documento PDF: " + e.getMessage());
                response.getWriter().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/Download-MembresiaPdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void  compraMembresiaPDF(@PathVariable Integer id, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_membresia" + id + ".pdf");

            membresiaFactura.buildPdfDocument(id, response);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("Error al generar el documento PDF: " + e.getMessage());
                response.getWriter().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @GetMapping("/last/{id_cliente}")
    public Optional<Factura> getLastFactura(@PathVariable String id_cliente) {
        return facturaService.findLastFactura(id_cliente);
    }
}

