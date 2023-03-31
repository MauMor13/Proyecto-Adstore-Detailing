package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.EmailDTO;
import mindhub.adstoreDetailing.servicios.ExportadorPDF;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ControladorEnvioEmail {
    private final EmailSenderService emailSenderService;
    private final ExportadorPDF exportadorPDF;

    public ControladorEnvioEmail(EmailSenderService emailSenderService, ExportadorPDF exportadorPDF) {
        this.emailSenderService = emailSenderService;
        this.exportadorPDF = exportadorPDF;
    }

    @PostMapping("/enviar-email")
    public ResponseEntity<?> enviarEmail(@RequestBody EmailDTO emailDTO ) throws MessagingException, IOException {
            this.emailSenderService.enviarEmail(emailDTO.getAsunto(), emailDTO.getMensaje(), emailDTO.getPara());
            return ResponseEntity.ok("Success");
    }
}
