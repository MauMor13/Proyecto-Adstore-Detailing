package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Categoria;
import mindhub.adstoreDetailing.models.Producto;

@Getter
@NoArgsConstructor
public class ProductoDTO {
    private long id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenUrl;
    private Categoria categoria;
    private boolean activo;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.descripcion = producto.getDescripcion();
        this.imagenUrl = producto.getImagenUrl();
        this.categoria = producto.getCategoria();
        this.activo = producto.isActivo();
    }
}