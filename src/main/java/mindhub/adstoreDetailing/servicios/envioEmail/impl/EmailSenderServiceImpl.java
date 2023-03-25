package mindhub.adstoreDetailing.servicios.envioEmail.impl;

import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarEmail(String asunto, String mensaje, String para) throws MessagingException {
       MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("adstoreDetailing23@gmail.com");
        mimeMessageHelper.setTo(para);
        mimeMessageHelper.setSubject(asunto);
        mimeMessageHelper.setText(mensaje);

        FileSystemResource fileSystemResource = new FileSystemResource(new File())

        this.mailSender.send(mimeMailMessage);
    }
}
