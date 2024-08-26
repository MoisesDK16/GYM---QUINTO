package com.gym.services.files;

import com.gym.models.Detalle;
import com.gym.models.Factura;
import com.gym.services.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("ListarDetallesFactura")
public class ListarDetallesFactura{

    private final DetalleService detalleService;

    @Autowired
    public ListarDetallesFactura(DetalleService detalleService) {
        this.detalleService = detalleService;
    }

    public void buildPdfDocument(Integer facturaId,Document document, PdfWriter writer,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Obtén los detalles de la factura por su ID
        List<Detalle> listarDetallesFactura = detalleService.listarDetallesFactura(facturaId);
        if (listarDetallesFactura.isEmpty()) {
            throw new Exception("No se encontraron detalles para la factura con ID " + facturaId);
        }

        Factura facturaAsoc = listarDetallesFactura.get(listarDetallesFactura.size() - 1).getFactura();

        // Configuración del documento PDF
        document.setPageSize(PageSize.LETTER);
        document.setMargins(36, 36, 72, 36);
        document.open();

        // Fuente personalizada
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.BLACK);
        Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        Font fuenteTexto = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

        // Título
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celdaTitulo = new PdfPCell(new Phrase("Gym Ciudad Verde", fuenteTitulo));
        celdaTitulo.setBorder(0);
        celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTitulo.setPadding(15);
        tablaTitulo.addCell(celdaTitulo);
        tablaTitulo.setSpacingAfter(20);
        document.add(tablaTitulo);

        // Datos de la factura y del cliente
        PdfPTable tablaInfo = new PdfPTable(2);
        tablaInfo.setWidthPercentage(100);
        tablaInfo.setSpacingAfter(20);

        PdfPCell celdaFecha = new PdfPCell(new Phrase("Fecha: " + facturaAsoc.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fuenteTexto));
        celdaFecha.setBorder(0);
        tablaInfo.addCell(celdaFecha);

        PdfPCell celdaCliente = new PdfPCell(new Phrase("Datos del Cliente:\n" +
                "Identificación: " + facturaAsoc.getCliente().getId_cliente() + "\n" +
                "Nombre: " + facturaAsoc.getCliente().getNombre() + "\n" +
                "Mail: " + facturaAsoc.getCliente().getCorreo() + "\n" +
                "Teléfono: " + facturaAsoc.getCliente().getTelefono(), fuenteTexto));
        celdaCliente.setBorder(0);
        tablaInfo.addCell(celdaCliente);

        document.add(tablaInfo);

        // Tabla de conceptos
        PdfPTable tablaConceptos = new PdfPTable(4);
        tablaConceptos.setWidths(new float[]{4f, 1f, 1f, 1f});
        tablaConceptos.setWidthPercentage(100);
        tablaConceptos.setSpacingAfter(20);

        // Cabecera de la tabla de conceptos
        String[] subtitulos = {"Concepto", "Cantidad", "Precio", "Total"};
        for (String subtitulo : subtitulos) {
            PdfPCell celdaSubtitulo = new PdfPCell(new Phrase(subtitulo, fuenteSubtitulo));
            celdaSubtitulo.setBackgroundColor(new Color(0, 102, 204)); // Azul oscuro
            celdaSubtitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaSubtitulo.setPadding(8);
            tablaConceptos.addCell(celdaSubtitulo);
        }

        // Detalles de los conceptos
        for (Detalle detalle : listarDetallesFactura) {
            tablaConceptos.addCell(new PdfPCell(new Phrase(detalle.getProducto().getNombre(), fuenteTexto)));
            tablaConceptos.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fuenteTexto)));
            tablaConceptos.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getPrecio()), fuenteTexto)));
            tablaConceptos.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getTotal()), fuenteTexto)));
        }

        document.add(tablaConceptos);

        // Tabla de totales
        PdfPTable tablaTotales = new PdfPTable(2);
        tablaTotales.setWidthPercentage(40);
        tablaTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Añadir filas de totales
        tablaTotales.addCell(new PdfPCell(new Phrase("Subtotal", fuenteTexto)));
        tablaTotales.addCell(new PdfPCell(new Phrase(String.format("%.2f $", facturaAsoc.getSubtotal()), fuenteTexto)));

        tablaTotales.addCell(new PdfPCell(new Phrase("IVA (15%)", fuenteTexto)));
        tablaTotales.addCell(new PdfPCell(new Phrase(String.format("%.2f $", facturaAsoc.getIva()), fuenteTexto)));

        tablaTotales.addCell(new PdfPCell(new Phrase("Total", fuenteTexto)));
        tablaTotales.addCell(new PdfPCell(new Phrase(String.format("%.2f $", facturaAsoc.getTotal()), fuenteTexto)));

        document.add(tablaTotales);

        // Método de pago
        PdfPTable tablaPago = new PdfPTable(1);
        tablaPago.setWidthPercentage(100);
        tablaPago.setSpacingBefore(20);

        PdfPCell celdaPago = new PdfPCell(new Phrase("Forma de pago: " + facturaAsoc.getMetodoPago(), fuenteTexto));
        celdaPago.setBorder(0);
        tablaPago.addCell(celdaPago);

        document.add(tablaPago);
    }
}
