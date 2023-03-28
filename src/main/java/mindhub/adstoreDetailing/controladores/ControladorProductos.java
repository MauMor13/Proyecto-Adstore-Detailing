package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ProductoACrearDTO;
import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<Object> modificarProducto(@RequestBody ProductoDTO producto){

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

    @PostMapping("/crear-producto")
    public ResponseEntity<Object> crearProducto(@RequestBody ProductoACrearDTO productoACrear){
        if(productoACrear.getPrecio()<0){
            return new ResponseEntity<>("Ingrese Precio", HttpStatus.BAD_REQUEST);
        }
        if(productoACrear.getDescripcion()==null){
            return new ResponseEntity<>("Ingrese Descripción", HttpStatus.BAD_REQUEST);
        }
        if(productoACrear.getNombre()==null){
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if(productoACrear.getImagenUrl()==null){
            return new ResponseEntity<>("Ingrese Imagen", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getStock()<0){
            return new ResponseEntity<>("Ingrese Descripción", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getCategoria()==null){
            return new ResponseEntity<>("Ingrese Categoría", HttpStatus.BAD_REQUEST);
        }

        Producto productoCreado = new Producto(
                productoACrear.getNombre(),
                productoACrear.getPrecio(),
                productoACrear.getStock(),
                productoACrear.getDescripcion(),
                productoACrear.getImagenUrl(),
                productoACrear.getCategoria());

        this.servicioProducto.guardar(productoCreado);

        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }
}
