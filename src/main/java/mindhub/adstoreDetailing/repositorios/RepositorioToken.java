package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.TokenValidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;
@RepositoryRestResource
public interface RepositorioToken extends JpaRepository<TokenValidacion, Long> {
   Optional<TokenValidacion> findByToken(String token);
}
