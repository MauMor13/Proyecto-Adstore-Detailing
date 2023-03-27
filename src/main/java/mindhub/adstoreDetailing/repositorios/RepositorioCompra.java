package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface RepositorioCompra extends JpaRepository <Compra, Long> {
}
