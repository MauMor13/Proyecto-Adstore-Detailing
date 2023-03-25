package mindhub.adstoreDetailing.repositorios;

import mindhub.adstoreDetailing.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioServicio extends JpaRepository <Servicio, Long>{
}
