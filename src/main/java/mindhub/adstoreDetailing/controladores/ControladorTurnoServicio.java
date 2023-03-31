package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.TurnoServicioDTO;
import mindhub.adstoreDetailing.servicios.ServicioTurnoServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorTurnoServicio {
    private ServicioTurnoServicio servicioTurnoServicio;
    public ControladorTurnoServicio (ServicioTurnoServicio servicioTurnoServicio){this.servicioTurnoServicio = servicioTurnoServicio;}

    @GetMapping("/turnos")
    public List<TurnoServicioDTO> retornaTurno(){ return servicioTurnoServicio.findAllTurnoServicioDTO(); }
}
