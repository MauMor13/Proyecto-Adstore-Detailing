package mindhub.adstoreDetailing.servicios.envioEmail;

import mindhub.adstoreDetailing.models.Compra;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSenderService {
    void enviarEmail(String desde, String para, String mensaje) throws MessagingException, IOException;
}
