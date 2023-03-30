package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public TurnoServicio(LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida) {
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
    }
}
