package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_id_seq", allocationSize = 1)
    private long id;
    private String nombre;
    private double precio;
    private int stock;
    @Lob
    private String descripcion;
    private String imagenUrl;
    private Categoria categoria;
    private boolean activo;
    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<CompraProducto> compraProducto = new HashSet<>();
    public Producto(String nombre, double precio, int stock, String descripcion, String imagenUrl, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.activo = true;
    }
}
