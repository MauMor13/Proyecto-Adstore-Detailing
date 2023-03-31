package mindhub.adstoreDetailing.servicios.envioEmail.impl;

import mindhub.adstoreDetailing.models.Compra;

import mindhub.adstoreDetailing.models.TokenValidacion;

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
import java.nio.file.Files;

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

        mimeMessageHelper.setFrom("adstoreDetailing23@gmail.com");
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
    public void enviarCodigo(String para, TokenValidacion tokenValidacion) throws MessagingException {

        String contenido= "<p>Por favor haga click en el link para confirmar su correo: </p>";
        String urlVerificacion = "http://localhost:8080/api/confirmar-registro?token="+tokenValidacion.getToken();
        contenido+="<h3><a href=\"" + urlVerificacion+ "\">VERIFICAR</a></h3>";
        contenido+="<p>Gracias</p>";

        MimeMessage message = mailSender.createMimeMessage();
        message.setContent(contenido, "text/html; charset=utf-8");
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("adstoremailingservice@gmail.com");
        helper.setTo(para);
        helper.setSubject("Confirmación de email |Adstore");

        mailSender.send(message);
    }

    @GetMapping("/confirmar-registro")
    public void confirmarRegistro(@RequestParam String token){

    }

}
