package mindhub.adstoreDetailing.servicios;

import mindhub.adstoreDetailing.dtos.ServicioDTO;
import mindhub.adstoreDetailing.models.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioServicio {

    List<ServicioDTO> findAllDTOs();
    Optional<Servicio> findById(Long id);

}
