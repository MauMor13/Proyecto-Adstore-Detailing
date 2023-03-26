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
public class CompraProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_producto_seq")
    @SequenceGenerator(name = "compra_producto_seq", sequenceName = "compra_producto_id_seq", allocationSize = 1)
    private long id;
    private double precio;
    private double montoTotal;
    private int cantidad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Compra compra;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
