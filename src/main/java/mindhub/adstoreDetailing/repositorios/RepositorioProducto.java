package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RepositoryRestResource
public interface RepositorioProducto extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
}
