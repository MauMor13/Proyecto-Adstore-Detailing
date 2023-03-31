package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.TurnoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface RepositorioTurnoServicio extends JpaRepository<TurnoServicio, Long> {
    List<TurnoServicio>  findByFechaHoraIngresoBetween(LocalDateTime now, LocalDateTime by);
}
