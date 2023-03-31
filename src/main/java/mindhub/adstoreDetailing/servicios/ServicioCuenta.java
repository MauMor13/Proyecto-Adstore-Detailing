package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.models.Cuenta;
import org.springframework.stereotype.Service;


public interface ServicioCuenta {
    void guardar(Cuenta cuenta);
}
