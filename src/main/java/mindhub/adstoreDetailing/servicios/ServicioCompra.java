package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.models.Compra;
import org.springframework.stereotype.Service;

@Service
public interface ServicioCompra {
    void guardar(Compra compra);
}
