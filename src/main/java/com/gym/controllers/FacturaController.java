package com.gym.controllers;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.services.files.ListarDetallesFactura;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ListarDetallesFactura listarDetallesFactura;

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

    @GetMapping("/DownloadPdf/{facturaId}")
    public void downloadFacturaPdf(@PathVariable Integer facturaId, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"facturaPersonal.pdf\"");

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        try {
            document.open();
            listarDetallesFactura.buildPdfDocument(facturaId, document, writer, null, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } finally {
            document.close();
        }
    }

    @GetMapping("/last/{id_cliente}")
    public Optional<Factura> getLastFactura(@PathVariable String id_cliente) {
        return facturaService.findLastFactura(id_cliente);
    }
}
