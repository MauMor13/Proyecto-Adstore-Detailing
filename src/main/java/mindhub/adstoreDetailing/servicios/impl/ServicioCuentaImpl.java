package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import mindhub.adstoreDetailing.servicios.ServicioCuenta;
import org.springframework.stereotype.Service;

@Service
public class ServicioCuentaImpl implements ServicioCuenta {
    private final RepositorioCuenta repositorioCuenta;
    public ServicioCuentaImpl(RepositorioCuenta repositorioCuenta) {
        this.repositorioCuenta = repositorioCuenta;
    }
    @Override
    public void guardar(Cuenta cuenta){
        this.repositorioCuenta.save(cuenta);
    }
}