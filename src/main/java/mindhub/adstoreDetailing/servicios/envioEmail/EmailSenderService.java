package mindhub.adstoreDetailing.servicios.envioEmail;

import mindhub.adstoreDetailing.models.Compra;

import mindhub.adstoreDetailing.models.TokenValidacion;
import mindhub.adstoreDetailing.models.TurnoServicio;


import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EmailSenderService {
    void enviarEmail(String desde, String para, String mensaje) throws MessagingException, IOException;
    void enviarFactura(String asunto, String mensaje, String para, Compra compra)throws MessagingException, IOException;
    void enviarCodigo(String para,TokenValidacion tokenValidacion) throws MessagingException, UnsupportedEncodingException;
    void enviarRecordatorio(String asunto,String para, TurnoServicio turno)throws MessagingException, IOException;
}
