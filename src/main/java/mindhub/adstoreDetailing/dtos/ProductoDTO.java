package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Categoria;
import mindhub.adstoreDetailing.models.CompraProducto;
import mindhub.adstoreDetailing.models.Producto;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductoDTO {
    private long id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenUrl;
    private Categoria categoria;
    private Set<CompraProductoDTO> compraProducto;
    public ProductoDTO(Producto producto){
        this.id= producto.getId();
        this.nombre= producto.getNombre();
        this.precio= producto.getPrecio();
        this.stock= producto.getStock();
        this.descripcion= producto.getDescripcion();
        this.imagenUrl= producto.getImagenUrl();
        this.categoria=producto.getCategoria();
        this.compraProducto=producto.getCompraProducto().stream().map(CompraProductoDTO::new).collect(Collectors.toSet());
    }
}