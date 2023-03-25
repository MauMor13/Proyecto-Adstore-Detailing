package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_id_seq", allocationSize = 1)
    private long id;
    private String productoNombre;
    private double precio;
    private int stock;
    private String descripcion;
    private int descuento;
    private String imagenURL;

    @OneToMany(mappedBy = "productos",fetch = FetchType.EAGER)
    private CompraProducto compraProducto;

}
