package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq")
    @SequenceGenerator(name = "compra_seq", sequenceName = "compra_id_seq", allocationSize = 1)
    private long id;
    private double montoFinal;
    private LocalDateTime fecha;
    private int descuento;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    @OneToMany(mappedBy = "compra",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CompraProducto> compraProductos = new HashSet<>();
    @OneToMany(mappedBy = "compra",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CompraServicio> compraServicios = new HashSet<>();

    public Compra(double montoFinal, LocalDateTime fecha, int descuento) {
        this.montoFinal = montoFinal;
        this.fecha = fecha;
        this.descuento = descuento;
    }
    public void sumarCompraProducto(CompraProducto compraProducto){
        compraProducto.setCompra(this);
        compraProductos.add(compraProducto);
    }
    public void sumarCompraServicio(CompraServicio compraServicio){
        compraServicio.setCompra(this);
        compraServicios.add(compraServicio);
    }

    public double calcularPrecioTotal() {
        double precioTotal = 0.0;
        for (CompraProducto compraProducto : compraProductos) {
            precioTotal += compraProducto.getProducto().getPrecio() * compraProducto.getCantidad();
        }
        for (CompraServicio compraServicio : compraServicios) {
            precioTotal += compraServicio.getServicio().getPrecio();
        }
        if (descuento>0)
            precioTotal -= precioTotal * 0.05;

        return precioTotal;
    }

}
