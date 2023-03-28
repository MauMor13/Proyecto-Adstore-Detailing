package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.dtos.ServicioDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.models.Servicio;
import mindhub.adstoreDetailing.repositorios.RepositorioServicio;
import mindhub.adstoreDetailing.servicios.ServicioServicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ServicioServicioImpl implements ServicioServicio {
    private final RepositorioServicio repositorioServicio;

    public ServicioServicioImpl(RepositorioServicio repositorioServicio) {
        this.repositorioServicio = repositorioServicio;
    }

    @Override
    public List<ServicioDTO> findAllServicioDTO() {
        return this.mapListToDTOs(this.repositorioServicio.findAll());
    }

    private List<ServicioDTO> mapListToDTOs(List<Servicio> lista) {
        return lista.stream().map(ServicioDTO::new).collect(Collectors.toList());
    }
}
