package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ProductoACrearDTO;
import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Categoria;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControladorProducto {
    private final ServicioProducto servicioProducto;
    public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @GetMapping("/productos")
    public List<ProductoDTO> traerProductos() {
        return servicioProducto.findAllDTOs();
    }
    @GetMapping("/productos-activos")
    public List<ProductoDTO> productosActivosDTOs(){
        return this.servicioProducto.findByActiveTrueDTO();
    }

    @PatchMapping("/modificar-producto")
    public ResponseEntity<Object> modificarProducto(@RequestBody(required = false) ProductoDTO producto) {

        Optional<Producto> productoAModificar = this.servicioProducto.findById(producto.getId());

        StringBuilder modificadosSb = new StringBuilder().append("Se modificó: ");

        if (productoAModificar.isEmpty()) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.BAD_REQUEST);
        }
        if (producto.getPrecio() > 0) {
            productoAModificar.get().setPrecio(producto.getPrecio());
            modificadosSb.append("precio, ");
        }
        if (producto.getDescripcion() != null) {
            productoAModificar.get().setDescripcion(producto.getDescripcion());
            modificadosSb.append("descripción, ");
        }
        if (producto.getNombre() != null) {
            productoAModificar.get().setNombre(producto.getNombre());
            modificadosSb.append("nombre, ");
        }
        if (producto.getImagenUrl() != null) {
            productoAModificar.get().setImagenUrl(producto.getImagenUrl());
            modificadosSb.append("imagen, ");
        }
        if (producto.getStock() >= 0) {
            productoAModificar.get().setStock(producto.getStock());
            modificadosSb.append("stock, ");
        }
        if(producto.isActivo()){
            productoAModificar.get().setActivo(true);
            modificadosSb.append("producto activo, ");
        }
        modificadosSb.delete(modificadosSb.length() - 2, modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        servicioProducto.guardar(productoAModificar.get());
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }

    @PostMapping("/crear-producto")
    public ResponseEntity<Object> crearProducto(@RequestBody ProductoACrearDTO productoACrear) {
        if (productoACrear.getPrecio() < 0) {
            return new ResponseEntity<>("Ingrese Precio", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getDescripcion() == null) {
            return new ResponseEntity<>("Ingrese Descripción", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getNombre() == null) {
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getImagenUrl() == null) {
            return new ResponseEntity<>("Ingrese Imagen", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getStock() < 0) {
            return new ResponseEntity<>("Ingrese Descripción", HttpStatus.BAD_REQUEST);
        }
        if (productoACrear.getCategoria() == null || !EnumSet.allOf(Categoria.class).contains(productoACrear.getCategoria())) {
            return new ResponseEntity<>("Categoría inválida", HttpStatus.BAD_REQUEST);
        }

        Producto productoCreado = new Producto(productoACrear.getNombre(), productoACrear.getPrecio(), productoACrear.getStock(), productoACrear.getDescripcion(), productoACrear.getImagenUrl(), productoACrear.getCategoria());

        this.servicioProducto.guardar(productoCreado);

        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    @PatchMapping("/deshabilitar-producto")
    public ResponseEntity<Object> deshabilitarOHabilitar(@RequestParam Long id){

        Optional<Producto> producto = servicioProducto.findById(id);

        if(producto.isEmpty()){
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.BAD_REQUEST);
        }

        producto.get().setActivo(!producto.get().isActivo());

        servicioProducto.guardar(producto.get());

        return new ResponseEntity<>(producto.get().getNombre()+" "+"Producto activo: " + producto.get().isActivo(), HttpStatus.OK);
    }
}
