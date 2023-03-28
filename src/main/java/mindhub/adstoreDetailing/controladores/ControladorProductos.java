package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    @PatchMapping("/modificar-producto")
    public ResponseEntity<Object> modificarProducto(@RequestBody Producto producto){
        Optional<Producto> productoAModificar = this.servicioProducto.findById(producto.getId());
        StringBuilder modificadosSb = new StringBuilder().append("Se modificó: ");

        if (productoAModificar.isEmpty()){
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.BAD_REQUEST);
        }
        if(producto.getPrecio()>0 ){
            productoAModificar.get().setPrecio(producto.getPrecio());
            modificadosSb.append("precio, ");
        }
        if(producto.getDescripcion()!=null){
            productoAModificar.get().setDescripcion(producto.getDescripcion());
            modificadosSb.append("descripción, ");
        }
        if(producto.getNombre()!=null){
            productoAModificar.get().setNombre(producto.getNombre());
            modificadosSb.append("nombre, ");
        }
        if(producto.getImagenUrl()!=null){
            productoAModificar.get().setImagenUrl(producto.getImagenUrl());
            modificadosSb.append("imagen, ");
        }
        if (producto.getStock()>=0){
            productoAModificar.get().setStock(producto.getStock());
            modificadosSb.append("stock, ");
        }
        modificadosSb.delete(modificadosSb.length()-2,modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }
}
