package mindhub.adstoreDetailing.servicios.impl;
import mindhub.adstoreDetailing.dtos.TurnoServicioDTO;
import mindhub.adstoreDetailing.models.TurnoServicio;
import mindhub.adstoreDetailing.repositorios.RepositorioTurnoServicio;
import mindhub.adstoreDetailing.servicios.ServicioTurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioTurnoServicioImpl implements ServicioTurnoServicio {
    @Autowired
    RepositorioTurnoServicio repositorioTurnoServicio;
    @Override
    public List<TurnoServicioDTO> findAllTurnoServicioDTO() {
        return repositorioTurnoServicio.findAll().stream().map(turnoServicio -> new TurnoServicioDTO(turnoServicio)).collect(Collectors.toList());
    }
}
