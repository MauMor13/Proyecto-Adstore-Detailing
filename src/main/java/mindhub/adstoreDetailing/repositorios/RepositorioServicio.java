package mindhub.adstoreDetailing.repositorios;

import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RepositorioServicio extends JpaRepository <Servicio, Long>{
    List<Servicio> findByActivoTrue();
}
