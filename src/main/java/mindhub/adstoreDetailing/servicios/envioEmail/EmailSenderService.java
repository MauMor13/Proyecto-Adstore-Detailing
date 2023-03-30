package mindhub.adstoreDetailing.servicios.envioEmail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSenderService {
    void enviarEmail(String desde, String para, String mensaje) throws MessagingException, IOException;
    void enviarFactura(String asunto, String mensaje, String para)throws MessagingException, IOException;
}
