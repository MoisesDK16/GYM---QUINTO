package com.gym.controllers;


import com.gym.models.Factura;
import com.gym.services.FacturaService;
import com.lowagie.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class FileController extends AbstractPdfView {

    private final FacturaService facturaService;

    public FileController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"facturas.pdf\"");

        List<Factura> listaFacturas = (List<Factura>) model.get("factura");

        PdfPTable tablaFacturas = new PdfPTable(8);

        listaFacturas.forEach((factura) -> {
            tablaFacturas.addCell(factura.getIdFactura().toString());
            tablaFacturas.addCell(factura.getCliente().getId_cliente());
            tablaFacturas.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getPrimer_apellido());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tablaFacturas.addCell(factura.getFechaEmision().format(formatter));
            tablaFacturas.addCell(factura.getMetodoPago());
            tablaFacturas.addCell(factura.getSubtotal().toString());
            tablaFacturas.addCell(factura.getIva().toString());
            tablaFacturas.addCell(factura.getTotal().toString());
        });

        document.add(tablaFacturas);
    }

}
