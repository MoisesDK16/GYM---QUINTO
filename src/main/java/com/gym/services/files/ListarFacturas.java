package com.gym.services.files;

import com.gym.models.Factura;
import com.gym.services.FacturaService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ListarFacturas extends AbstractPdfView {

    @Autowired
    private FacturaService facturaService;

    public List<Factura> ListarFacturas() {
        return facturaService.listarFacturas();
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    jakarta.servlet.http.HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"facturas.pdf\"");

        @SuppressWarnings("unchecked")
        List<Factura> listaFacturas = (List<Factura>) model.get("facturas");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(10, 10, 30, 10);
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = new PdfPCell(new Phrase("FACTURACION DE PRODUCTOS", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));

        celda.setBorder(0);
        celda.setBackgroundColor(Color.CYAN);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(15);

        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaFacturas = new PdfPTable(8);
        tablaFacturas.setWidths(new float[]{1f, 1.5f, 2f, 1.2f, 1.2f, 1.2f, 1f, 1f});

        String[] subtitulos = {"FACTURA", "ID CLIENTE", "NOMBRE", "FECHA", "METODO PAGO", "SUBTOTAL", "IVA", "TOTAL"};

        for (String subtitulo : subtitulos) {
            PdfPCell celdaSubtitulo = new PdfPCell(new Phrase(subtitulo));
            celdaSubtitulo.setBackgroundColor(Color.GREEN);
            celdaSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaFacturas.addCell(celdaSubtitulo);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Factura factura : listaFacturas) {
            tablaFacturas.addCell(factura.getIdFactura().toString());
            tablaFacturas.addCell(factura.getCliente().getId_cliente());
            tablaFacturas.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getPrimer_apellido());
            tablaFacturas.addCell(factura.getFechaEmision().format(formatter));
            tablaFacturas.addCell(factura.getMetodoPago());
            tablaFacturas.addCell(factura.getSubtotal().toString());
            tablaFacturas.addCell(factura.getIva().toString());
            tablaFacturas.addCell(factura.getTotal().toString());
        }

        document.add(tablaTitulo);
        document.add(tablaFacturas);
    }
}

