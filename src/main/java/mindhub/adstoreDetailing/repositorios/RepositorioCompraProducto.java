package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.CompraProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface RepositorioCompraProducto extends JpaRepository <CompraProducto, Long> {
}
