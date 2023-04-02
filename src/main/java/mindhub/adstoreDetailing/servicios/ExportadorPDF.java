package mindhub.adstoreDetailing.servicios;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraProducto;
import mindhub.adstoreDetailing.models.CompraServicio;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ExportadorPDF {
   // private List<Transaction> transactionList;
   private Set<CompraProducto> compraProductos;
   private Set<CompraServicio> compraServicios;

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.green);
        cell.setPadding(5);

       Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
       font.setColor(Color.MAGENTA);

        cell.setPhrase(new Phrase("Date",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sender",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Remaining Balance",font));
        table.addCell(cell);
    }
    private void writeTableHeaderProductos(PdfPTable table){
        Color amarilloAdstore = new Color(245,184,37);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(amarilloAdstore);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Producto",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cantidad",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio unitario",font));
        table.addCell(cell);
    }
    private void writeTableHeaderServicios(PdfPTable table){
        Color amarilloAdstore = new Color(245,184,37);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(amarilloAdstore);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Servicio",font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio",font));
        table.addCell(cell);
    }
    private void writeTableDataServicio(PdfPTable table ,Set<CompraServicio> compraServicios){
        for (CompraServicio compraServicio : compraServicios){
            table.addCell(compraServicio.getServicio().getNombre());
            table.addCell(String.valueOf(compraServicio.getServicio().getPrecio()));
        }
    }


    private void writeTableDataProducto(PdfPTable table, Set<CompraProducto> compraProductos ){
        for (CompraProducto compraProducto : compraProductos){
            table.addCell(compraProducto.getProducto().getNombre());
            table.addCell(String.valueOf(compraProducto.getCantidad()));
            table.addCell(String.valueOf(compraProducto.getProducto().getPrecio()));
        }
    }

    private void writeTableData(PdfPTable table ){
//        for (Transaction transaction : transactionList){
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String formattedDate = transaction.getDate().format(formatter);
//            table.addCell(formattedDate);
//            table.addCell(String.valueOf(transaction.getAmount()));
//            table.addCell(String.valueOf(transaction.getDescription()));
//            table.addCell(String.valueOf(transaction.getType()));
//            table.addCell(String.valueOf(transaction.getSender() == null ? "N/A" : String.valueOf(transaction.getSender())));
//            table.addCell(String.valueOf(transaction.getRemainingBalance()));
//        }
    }

    public void generarPdf() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("sample.pdf"));
            document.open();
            document.add(new Paragraph("Hello, World!"));
            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    public void generarFactura(Compra compra) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
            document.open();

            Image image = Image.getInstance("src/main/resources/static/web/assets/Imagenes/PNG9.png");
            image.scalePercent(25F, 25F);
//            image.setAlignment( Image.ALIGN_TOP | Image.ALIGN_MIDDLE);
//            document.add(image);

            StringBuilder sb = new StringBuilder();
            sb.append("Adstore Detailing\n");
            sb.append("Pedro Carta Molina 650, X5016 Córdoba\n");
            sb.append("0351 319-3271");

            // Create a paragraph object
            Paragraph paragraph = new Paragraph(sb.toString(), FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC));

            // Create a table with two columns
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            // Add the image to the left column
            PdfPCell cell = new PdfPCell(image);
            cell.setBorder(0);
            headerTable.addCell(cell);

            // Add the paragraph to the right column
            cell = new PdfPCell(paragraph);
            cell.setBorder(0);
            headerTable.addCell(cell);

            // Add the table to the PDF document
            document.add(headerTable);


            if (!compra.getCompraProductos().isEmpty()){
                PdfPTable tablaProductos = new PdfPTable(3);
                tablaProductos.setWidthPercentage(100);
                tablaProductos.setSpacingBefore(20);
                this.writeTableHeaderProductos(tablaProductos);
                this.writeTableDataProducto(tablaProductos, compra.getCompraProductos());
                document.add(tablaProductos);
            }

            if(!compra.getCompraServicios().isEmpty()){
                PdfPTable tablaServicios = new PdfPTable(2);
                tablaServicios.setWidthPercentage(100);
                tablaServicios.setSpacingBefore(20);
                tablaServicios.setSpacingAfter(10);
                this.writeTableHeaderServicios(tablaServicios);
                this.writeTableDataServicio(tablaServicios, compra.getCompraServicios());
                document.add(tablaServicios);
            }

            // Create a font object with underline
            Font fontUnderline = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.UNDERLINE);
            Paragraph montoTotal = new Paragraph("Monto Total: $"+ compra.getMontoFinal(), fontUnderline);
            montoTotal.setAlignment(Element.ALIGN_RIGHT);

            document.add(montoTotal);


            document.addTitle("facturaAdstore");
            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    public void export(HttpServletResponse response,  LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.add(new Paragraph("Transactions "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

    public byte[] export2(LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, baos);

        document.open();

        document.add(new Paragraph("Transactions "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

        return baos.toByteArray();
    }

}
