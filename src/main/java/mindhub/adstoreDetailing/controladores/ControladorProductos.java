package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ControladorProductos {
    @Autowired
    private RepositorioProducto repositorioProducto;

    @GetMapping("/productos")
    public List<ProductoDTO> traerProductos(){
        return repositorioProducto.findAll().stream().map(ProductoDTO::new).collect(Collectors.toList());
    }
}
