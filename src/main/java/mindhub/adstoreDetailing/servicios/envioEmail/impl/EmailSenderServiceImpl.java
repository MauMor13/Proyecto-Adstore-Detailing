package mindhub.adstoreDetailing.servicios.envioEmail.impl;

import mindhub.adstoreDetailing.models.Compra;

import mindhub.adstoreDetailing.models.CompraServicio;
import mindhub.adstoreDetailing.models.TokenValidacion;

import mindhub.adstoreDetailing.models.TurnoServicio;
import mindhub.adstoreDetailing.servicios.ExportadorPDF;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final ExportadorPDF exportadorPDF;

    public EmailSenderServiceImpl(JavaMailSender mailSender, ExportadorPDF exportadorPDF) {
        this.mailSender = mailSender;
        this.exportadorPDF = exportadorPDF;
    }

    @Override
    public void enviarEmail(String asunto, String mensaje, String para) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        exportadorPDF.generarPdf();

        mimeMessageHelper.setFrom("adstoreDetailing23@gmail.com");
        mimeMessageHelper.setTo(para);
        mimeMessageHelper.setSubject(asunto);
        mimeMessageHelper.setText(mensaje);

        File file = new File("sample.pdf");
        byte[] bytes = Files.readAllBytes(file.toPath());
        ByteArrayDataSource pdfDataSource = new ByteArrayDataSource(bytes, "application/pdf");

        // add the attachment to the email
        mimeMessageHelper.addAttachment("sample.pdf", pdfDataSource);

        this.mailSender.send(mimeMessage);

    }

    @Override
    public void enviarFactura(String asunto, String mensaje, String para, Compra compra) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        exportadorPDF.generarFactura(compra);

        mimeMessageHelper.setFrom("adstoreDetailing23@gmail.com", "Adstore Detailing");
        mimeMessageHelper.setTo(para);
        mimeMessageHelper.setSubject(asunto);

        String sb = mensaje +
                "\n";
        //sb.append("Turno :");
        // sb.append(compra.get)

        mimeMessageHelper.setText(sb);

        File file = new File("factura.pdf");
        byte[] bytes = Files.readAllBytes(file.toPath());
        ByteArrayDataSource pdfDataSource = new ByteArrayDataSource(bytes, "application/pdf");

        // add the attachment to the email
        mimeMessageHelper.addAttachment("factura.pdf", pdfDataSource);

        this.mailSender.send(mimeMessage);
    }

    @Override
    public void enviarCodigo(String para, TokenValidacion tokenValidacion) throws MessagingException, UnsupportedEncodingException {

        String contenido= "<h1>Por favor haga click en el link para confirmar su correo: </h1>";
        String urlVerificacion = "http://localhost:8080/api/confirmar-registro?token="+tokenValidacion.getToken();
        contenido+="<h2><a href=\"" + urlVerificacion+ "\">VERIFICAR</a></h2>";
        contenido+="<p>Gracias.</p>";
        contenido+="<p>-Adstore Detailing</p>";

        MimeMessage message = mailSender.createMimeMessage();
        message.setContent(contenido, "text/html; charset=utf-8");
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("adstoremailingservice@gmail.com", "Adstore Detailing");
        helper.setTo(para);
        helper.setSubject("Confirmación de email |Adstore");

        mailSender.send(message);
    }

    @Override
    public void enviarRecordatorio(String asunto, String para,TurnoServicio turno) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


        mimeMessageHelper.setFrom("adstoreDetailing23@gmail.com", "Adstore Detailing");
        mimeMessageHelper.setTo(para);
        mimeMessageHelper.setSubject(asunto);

        DateTimeFormatter formatterIngreso = DateTimeFormatter.ofPattern("EEEE dd MMMM");//agregar hora

        String fechaIngreso= formatterIngreso.format(turno.getFechaHoraIngreso());

        Duration duration = Duration.between(turno.getFechaHoraIngreso(), turno.getFechaHoraSalida());
        long minutes = duration.toMinutes();

        StringBuilder sb = new StringBuilder("Estimado cliente,\n");
        sb.append("\n");
        sb.append("Le escribimos para recordarle que hoy ");
        sb.append(fechaIngreso);
        sb.append(" tiene turno con nosotros para ");
        for(CompraServicio compraServicio : turno.getCompraServicios()){
            sb.append(compraServicio.getServicio().getNombre());
            sb.append(" , ");
        }
        sb.replace(sb.length()-2, sb.length(), " .");
        sb.append("\n");
        sb.append("\n");
        sb.append("Tiempo estimado de servicio : ");
        sb.append(minutes).append(" minutos");
        sb.append(".");

        mimeMessageHelper.setText(sb.toString());


        this.mailSender.send(mimeMessage);
    }


}
