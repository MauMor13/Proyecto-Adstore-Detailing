package mindhub.adstoreDetailing.repositorios;

import mindhub.adstoreDetailing.models.TarjetaMiembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioTarjetaMiembro extends JpaRepository<TarjetaMiembro, Long> {
}
