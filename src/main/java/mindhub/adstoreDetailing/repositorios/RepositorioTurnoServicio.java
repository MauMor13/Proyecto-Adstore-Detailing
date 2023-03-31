package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.TurnoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioTurnoServicio extends JpaRepository<TurnoServicio, Long> {
}
