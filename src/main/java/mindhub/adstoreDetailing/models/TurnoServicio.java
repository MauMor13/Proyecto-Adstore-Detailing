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
public class TurnoServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuentaMiembro_seq")
    @SequenceGenerator(name = "cuentaMiembro_seq", sequenceName = "cuentaMiembro_id_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private boolean notificado=false;
    @OneToMany(mappedBy = "turnoServicio", fetch = FetchType.EAGER)
    private Set<CompraServicio> compraServicios = new HashSet<>();
    public TurnoServicio(LocalDateTime fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }
    public void sumarCompraServicio(CompraServicio compraServicio){
        compraServicio.setTurnoServicio(this);
        compraServicios.add(compraServicio);
    }
}
