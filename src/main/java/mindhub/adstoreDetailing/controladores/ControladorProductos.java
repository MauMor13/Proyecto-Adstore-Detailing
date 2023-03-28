package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ControladorProductos {
   private final ServicioProducto servicioProducto;
    public ControladorProductos(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @GetMapping("/productos")
    public List<ProductoDTO> traerProductos(){
        return servicioProducto.findAllDTOs();
    }
}
