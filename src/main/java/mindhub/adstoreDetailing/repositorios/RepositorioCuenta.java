package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCuenta extends JpaRepository<Cuenta, Long> {
    boolean existsCuentaByNumeroCuenta(String numero);
}
