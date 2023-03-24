package mindhub.adstoreDetailing.servicios.envioEmail.impl;

import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarEmail(String asunto, String mensaje, String para) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("");
        simpleMailMessage.setTo(para);
        simpleMailMessage.setSubject(asunto);
        simpleMailMessage.setText(mensaje);

        this.mailSender.send(simpleMailMessage);

    }
}
