package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.EmailDTO;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorEnvioEmail {
    private final EmailSenderService emailSenderService;

    public ControladorEnvioEmail(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/enviar-email")
    public ResponseEntity<?> enviarEmail(@RequestBody EmailDTO emailDTO ){
            this.emailSenderService.enviarEmail(emailDTO.getAsunto(), emailDTO.getMensaje(), emailDTO.getPara());
            return ResponseEntity.ok("Success");
    }
}
