package mindhub.adstoreDetailing.repositorios;
import mindhub.adstoreDetailing.models.TarjetaAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface RepositorioTarjetaAd extends JpaRepository <TarjetaAd, Long> {
    boolean existsTarjetaByNumeroTarjeta(int numeroTarjeta);
}
