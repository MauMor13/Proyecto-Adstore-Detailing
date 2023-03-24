package mindhub.adstoreDetailing.repositorios;

import mindhub.adstoreDetailing.models.CuentaMiembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCuentaMiembro extends JpaRepository<CuentaMiembro, Long> {
}
