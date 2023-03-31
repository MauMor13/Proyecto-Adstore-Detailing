package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.dtos.ServicioDTO;
import mindhub.adstoreDetailing.models.Servicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ServicioServicio {
    List<ServicioDTO> findAllServicioDTO();
    Optional<Servicio> findById(Long id);
    public void guardar(Servicio servicio);

    List<Servicio> findByActiveTrue();
    public List<ServicioDTO> findByActiveTrueDTO();
    List<ServicioDTO> findAllServiciosActivosDTO();

}
