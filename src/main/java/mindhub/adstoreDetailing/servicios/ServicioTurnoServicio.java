package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.dtos.TurnoServicioDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicioTurnoServicio {
    List<TurnoServicioDTO> findAllTurnoServicioDTO();
    List<LocalDateTime> fechasOcupadas();
}
