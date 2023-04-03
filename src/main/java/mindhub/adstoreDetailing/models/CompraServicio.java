package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class CompraServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_servicio_seq")
    @SequenceGenerator(name = "compra_servicio_seq", sequenceName = "compra_servicio_id_seq", allocationSize = 1)
    private Long id;
    private double precio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Compra compra;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turnoServicio_id")
    private TurnoServicio turnoServicio;

    public CompraServicio(Compra compra, Servicio servicio) {
        this.precio = servicio.getPrecio();
        this.compra = compra;
        this.servicio = servicio;
    }
}
