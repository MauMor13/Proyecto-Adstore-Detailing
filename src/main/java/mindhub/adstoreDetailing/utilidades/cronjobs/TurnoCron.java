package mindhub.adstoreDetailing.utilidades.cronjobs;

import mindhub.adstoreDetailing.models.CompraServicio;
import mindhub.adstoreDetailing.models.TurnoServicio;
import mindhub.adstoreDetailing.repositorios.RepositorioTurnoServicio;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class TurnoCron {

    // Autowire necessary dependencies
    @Autowired
    RepositorioTurnoServicio repositorioTurnoServicio;
    @Autowired
    EmailSenderService emailSenderService;

    @Scheduled(cron = "0 */1 * * * *") // Run every 15 minutes
    public void checkAndSendEmails() throws MessagingException, IOException {
        System.out.println("Arranca");
        LocalDateTime now = LocalDateTime.now();
        List<TurnoServicio> turnos = repositorioTurnoServicio.findByFechaHoraIngresoBetween(
                now.with(LocalTime.MIN), now.with(LocalTime.MAX));
        System.out.println(turnos);
        for (TurnoServicio turno : turnos) {
            if(!turno.isNotificado()){
                String email = traerUno(turno.getCompraServicios()).getCompra().getCuenta().getCliente().getEmail();
               emailSenderService.enviarRecordatorio("Recordatorio turno | Adstore Detailing",email,turno);
               turno.setNotificado(true);
               repositorioTurnoServicio.save(turno);
               System.out.println("Email Enviado");
            }
        }
    }
    private CompraServicio traerUno(Set<CompraServicio> set){

        Iterator<CompraServicio> iterador = set.iterator();

        return iterador.next();
    }
}