package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.repositorios.RepositorioProducto;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public void guardar(Producto producto){
        this.repositorioProducto.save(producto);
    }

    @Override
    public List<ProductoDTO> productosActivosDTO() {
        return repositorioProducto.findAll().stream().filter(Producto::isActivo).map(ProductoDTO::new).collect(Collectors.toList());
    }
    @Override
    public Optional<Producto> findById(Long id){
       return this.repositorioProducto.findById(id);
    }

    @Override
    public List<Producto> findByActiveTrue(){
        return this.repositorioProducto.findByActivoTrue();
    }
    @Override
    public List<ProductoDTO> findByActiveTrueDTO(){
        return this.repositorioProducto.findByActivoTrue().stream().map(ProductoDTO::new).collect(Collectors.toList());
    }
    private List<ProductoDTO>mapListToDTOs(List<Producto> listaProducto){
        return listaProducto.stream().map(ProductoDTO::new).collect(Collectors.toList());

    }
}
