package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ServicioDTO;
import mindhub.adstoreDetailing.servicios.ServicioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorServicio {
    private final ServicioServicio servicioServicio;

    public ControladorServicio(ServicioServicio servicioServicio) {
        this.servicioServicio = servicioServicio;
    }

    @GetMapping("/servicios")
    public List<ServicioDTO> traerServicios() {
        return this.servicioServicio.findAllDTOs();
    }
}
