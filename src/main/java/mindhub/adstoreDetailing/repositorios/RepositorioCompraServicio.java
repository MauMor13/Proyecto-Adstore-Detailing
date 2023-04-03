package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.CompraServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCompraServicio extends JpaRepository<CompraServicio, Long>{
}
