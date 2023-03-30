package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.repositorios.RepositorioCompra;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCompraImpl implements ServicioCompra {
    @Autowired
    private RepositorioCompra repositorioCompra;
    @Override
    public void guardar(Compra compra) {
        repositorioCompra.save(compra);
    }
}
