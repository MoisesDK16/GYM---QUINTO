package com.gym.services.files;

import com.gym.models.Detalle;
import com.gym.models.Factura;
import com.gym.services.DetalleService;
import com.lowagie.text.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarDetallesFactura {

    private final DetalleService detalleService;
    private static final Image logoImg;

    static {
        try (InputStream fis = new ClassPathResource("img/logoGYM.JPG").getInputStream()) {
            byte[] imageBytes = fis.readAllBytes();
            logoImg = Image.getInstance(imageBytes);
            logoImg.scaleToFit(150f, 75f);
        } catch (IOException | com.lowagie.text.BadElementException e) {
            throw new IllegalStateException("Error al cargar el logotipo para el PDF.", e);
        }
    }

    public void buildPdfDocument(Integer facturaId, HttpServletResponse response) throws IOException {

        List<Detalle> listarDetallesFactura = detalleService.listarDetallesFactura(facturaId);
        if (listarDetallesFactura.isEmpty()) {
            throw new IOException("No se encontraron detalles para la factura con ID " + facturaId);
        }

        Factura facturaAsoc = listarDetallesFactura.get(0).getFactura();

        try (var baos = new ByteArrayOutputStream(); var document = new Document()) {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.setPageSize(PageSize.LETTER);
            document.setMargins(36, 36, 72, 36);

            document.add(initTable());
            document.add(getHeaderTable(facturaAsoc));

            var detallesTitle = new Paragraph("Detalles de Factura", new Font(Font.HELVETICA, 14, Font.BOLD));
            detallesTitle.setSpacingBefore(20f);
            detallesTitle.setSpacingAfter(10f);
            document.add(detallesTitle);

            document.add(getDetallesTable(listarDetallesFactura));
            document.add(getTotalesTable(facturaAsoc));
            document.add(getMetodoPagoTable(facturaAsoc));

            document.close(); // Cerrar el documento antes de escribir el flujo de salida

            // Escribir el contenido del PDF al flujo de salida de la respuesta
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush(); // Asegurar que el flujo se vacíe completamente
        } catch (DocumentException e) {
            throw new IOException("Error al generar el documento PDF.", e);
        }
    }

    private static PdfPTable initTable() {
        var initTable = new PdfPTable(2);
        initTable.setWidthPercentage(100);
        initTable.setWidths(new float[]{1f, 3f});
        initTable.setSpacingAfter(5f);

        var titleCell = new PdfPCell(new Phrase("Resumen de la Factura", new Font(Font.HELVETICA, 12, Font.BOLD)));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        titleCell.setPaddingTop(15f);

        var logoCell = new PdfPCell(logoImg);
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setMinimumHeight(30f);

        initTable.addCell(logoCell);
        initTable.addCell(titleCell);
        return initTable;
    }

    private static PdfPTable getHeaderTable(Factura factura) {
        var headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1f, 3f});
        headerTable.setSpacingAfter(20f);

        var clientCell = new PdfPCell(new Phrase(
                "Cliente ID: " + factura.getCliente().getId_cliente() + "\n" +
                        "Nombre: " + factura.getCliente().getNombre() + " " + factura.getCliente().getPrimer_apellido() + "\n\n" +
                        "Factura No. " + factura.getIdFactura() + "\n" +
                        "Fecha de Emisión: " + factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        clientCell.setBorder(Rectangle.NO_BORDER);
        clientCell.setPaddingTop(10f);
        headerTable.addCell(clientCell);

        var pageCell = new PdfPCell(new Phrase("Página 1 de 1"));
        pageCell.setBorder(Rectangle.NO_BORDER);
        pageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pageCell.setPaddingTop(10f);
        headerTable.addCell(pageCell);

        return headerTable;
    }

    private static PdfPTable getDetallesTable(List<Detalle> detalles) {
        var detallesTable = new PdfPTable(4);
        detallesTable.setWidths(new float[]{4f, 1f, 1f, 1f});
        detallesTable.setWidthPercentage(100);
        detallesTable.setSpacingAfter(20f);

        String[] subtitulos = {"Concepto", "Cantidad", "Precio", "Total"};
        for (String subtitulo : subtitulos) {
            var celdaSubtitulo = new PdfPCell(new Phrase(subtitulo, new Font(Font.HELVETICA, 10, Font.BOLD)));
            celdaSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaSubtitulo.setPadding(8);
            detallesTable.addCell(celdaSubtitulo);
        }

        for (Detalle detalle : detalles) {
            detallesTable.addCell(new PdfPCell(new Phrase(detalle.getProducto().getNombre(), new Font(Font.HELVETICA, 10))));
            detallesTable.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), new Font(Font.HELVETICA, 10))));
            detallesTable.addCell(new PdfPCell(new Phrase(String.format("%.2f", detalle.getPrecio()), new Font(Font.HELVETICA, 10))));
            detallesTable.addCell(new PdfPCell(new Phrase(String.format("%.2f", detalle.getTotal()), new Font(Font.HELVETICA, 10))));
        }

        return detallesTable;
    }

    private static PdfPTable getTotalesTable(Factura factura) {
        var totalesTable = new PdfPTable(2);
        totalesTable.setWidthPercentage(40);
        totalesTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totalesTable.addCell(new PdfPCell(new Phrase("Subtotal", new Font(Font.HELVETICA, 10))));
        totalesTable.addCell(new PdfPCell(new Phrase(String.format("%.2f $", factura.getSubtotal()), new Font(Font.HELVETICA, 10))));

        totalesTable.addCell(new PdfPCell(new Phrase("IVA (15%)", new Font(Font.HELVETICA, 10))));
        totalesTable.addCell(new PdfPCell(new Phrase(String.format("%.2f $", factura.getIva()), new Font(Font.HELVETICA, 10))));

        totalesTable.addCell(new PdfPCell(new Phrase("Total", new Font(Font.HELVETICA, 10))));
        totalesTable.addCell(new PdfPCell(new Phrase(String.format("%.2f $", factura.getTotal()), new Font(Font.HELVETICA, 10))));

        return totalesTable;
    }

    private static PdfPTable getMetodoPagoTable(Factura factura) {
        var metodoPagoTable = new PdfPTable(1);
        metodoPagoTable.setWidthPercentage(100);
        metodoPagoTable.setSpacingBefore(20f);

        var celdaPago = new PdfPCell(new Phrase("Forma de pago: " + factura.getMetodoPago(), new Font(Font.HELVETICA, 10)));
        celdaPago.setBorder(Rectangle.NO_BORDER);
        metodoPagoTable.addCell(celdaPago);

        return metodoPagoTable;
    }
}
