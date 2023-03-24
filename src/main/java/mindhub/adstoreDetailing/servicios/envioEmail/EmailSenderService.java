package mindhub.adstoreDetailing.servicios.envioEmail;

public interface EmailSenderService {
    void enviarEmail(String desde, String para, String mensaje);
}
