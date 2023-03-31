package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.dtos.TurnoServicioDTO;
import java.util.List;

public interface ServicioTurnoServicio {
    List<TurnoServicioDTO> findAllTurnoServicioDTO();
}
