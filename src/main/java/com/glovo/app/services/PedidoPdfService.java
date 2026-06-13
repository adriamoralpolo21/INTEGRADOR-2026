package com.glovo.app.services;

import com.glovo.app.entity.Pedido;
import com.glovo.app.entity.PedidoDetalle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PedidoPdfService {

    public byte[] generarPdf(Pedido pedido) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);

            doc.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Detalle de pedido #" + pedido.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            doc.add(title);

            // Info básica
            doc.add(new Paragraph("Fecha: " + pedido.getFechaCreacion()));
            doc.add(new Paragraph("Dirección: " + pedido.getDireccionEntrega()));
            doc.add(new Paragraph("Referencia: " + (pedido.getReferencia() != null ? pedido.getReferencia() : "—")));
            doc.add(new Paragraph("Método de pago: " + pedido.getMetodoPago()));
            doc.add(new Paragraph("Estado: " + pedido.getEstado()));
            doc.add(Chunk.NEWLINE);

            // Tabla de productos
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Producto");
            table.addCell("Cant.");
            table.addCell("Precio");
            table.addCell("Subtotal");

            for (PedidoDetalle det : pedido.getDetalles()) {
                table.addCell(det.getNombreProducto());
                table.addCell(String.valueOf(det.getCantidad()));
                table.addCell("S/ " + det.getPrecioUnitario());
                table.addCell("S/ " + det.getSubtotal());
            }

            doc.add(table);
            doc.add(Chunk.NEWLINE);

            // Totales
            doc.add(new Paragraph("Subtotal: S/ " + pedido.getSubtotal()));
            doc.add(new Paragraph("Envío: S/ " + pedido.getCostoEnvio()));
            doc.add(new Paragraph("Total: S/ " + pedido.getTotal(), new Font(Font.HELVETICA, 12, Font.BOLD)));

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF del pedido", e);
        }
    }
}
