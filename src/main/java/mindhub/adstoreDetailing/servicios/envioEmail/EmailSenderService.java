package mindhub.adstoreDetailing.servicios.envioEmail;

import javax.mail.MessagingException;

public interface EmailSenderService {
    void enviarEmail(String desde, String para, String mensaje) throws MessagingException;
}
