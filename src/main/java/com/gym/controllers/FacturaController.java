package com.gym.controllers;

import com.gym.models.Detalle;
import com.gym.models.Factura;
import com.gym.services.FacturaService;
import com.gym.services.files.MembresiaFactura;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
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

    @PostMapping("me/generar")
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


    @GetMapping(value = "me/DownloadPdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
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

    @GetMapping(value = "me/Download-MembresiaPdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
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


    @GetMapping("me/last/{id_cliente}")
    public Optional<Factura> getLastFactura(@PathVariable String id_cliente) {
        return facturaService.findLastFactura(id_cliente);
    }

    /*Filtros*/

    //Membresias

    @GetMapping("/all-membresia")
    public ResponseEntity<List<Detalle>> getAllMembresia() {
        List<Detalle> listaFacturas = facturaService.findFacturasWithMembresia();
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-membresia/by-fecha/{fechaInicio}/{fechaFin}/{metodoPago}")
    public ResponseEntity<List<Detalle>> getAllMembresiaByFecha(@PathVariable String fechaInicio, @PathVariable String fechaFin, @PathVariable String metodoPago) {
        List<Detalle> listaFacturas = facturaService.findFacturasMembresiasByFechaEmision(fechaInicio, fechaFin, metodoPago);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-membresia/by-cliente/{idCliente}")
    public ResponseEntity<List<Detalle>> getAllMembresiaByCliente(@PathVariable String idCliente) {
        List<Detalle> listaFacturas = facturaService.findFacturasMembresiasByCliente(idCliente);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-membresia/by-nombre-apellido/{nombreApellido}")
    public ResponseEntity<List<Detalle>> getAllMembresiaByNombreCompleto(@PathVariable String nombreApellido) {
        List<Detalle> listaFacturas = facturaService.findFacturasByNombreCompletoM(nombreApellido);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }


    //Productos

    @GetMapping("/all-productos")
    public ResponseEntity<List<Factura>> getAllProductos() {
        List<Factura> listaFacturas = facturaService.findFacturasWithProductos();
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-productos/by-fecha/{fechaInicio}/{fechaFin}/{metodoPago}")
    public ResponseEntity<List<Factura>> getAllProductosByFecha(@PathVariable String fechaInicio, @PathVariable String fechaFin, @PathVariable String metodoPago) {
        List<Factura> listaFacturas = facturaService.findFacturasProductosByFechaEmision(fechaInicio, fechaFin, metodoPago);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-productos/by-cliente/{idCliente}")
    public ResponseEntity<List<Factura>> getAllProductosByCliente(@PathVariable String idCliente) {
        List<Factura> listaFacturas = facturaService.findFacturasProductosByCliente(idCliente);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

    @GetMapping("/all-productos/by-nombre-apellido/{nombreApellido}")
    public ResponseEntity<List<Factura>> getAllProductosByNombreCompleto(@PathVariable String nombreApellido) {
        List<Factura> listaFacturas = facturaService.findFacturasByNombreCompleto(nombreApellido);
        return new ResponseEntity<>(listaFacturas, HttpStatus.OK);
    }

}

