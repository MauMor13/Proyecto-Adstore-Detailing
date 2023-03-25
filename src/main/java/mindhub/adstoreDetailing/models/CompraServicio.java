package mindhub.adstoreDetailing.models;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
public class CompraServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_servicio_seq")
    @SequenceGenerator(name = "compra_servicio_seq", sequenceName = "compra_servicio_id_seq", allocationSize = 1)
    private Long id;
    private Double precio;
    private Integer cantidad;
    private double montoTotal;
    private LocalDateTime fechaReserva;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Compra compra;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
}