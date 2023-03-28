package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.repositorios.RepositorioProducto;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioProductoImpl implements ServicioProducto {
    private final RepositorioProducto repositorioProducto;

    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public List<ProductoDTO> findAllDTOs(){
        return this.mapListToDTOs(this.repositorioProducto.findAll());
    }
    private List<ProductoDTO>mapListToDTOs(List<Producto> lista){
        return lista.stream().map(ProductoDTO::new).collect(Collectors.toList());
    }
}
