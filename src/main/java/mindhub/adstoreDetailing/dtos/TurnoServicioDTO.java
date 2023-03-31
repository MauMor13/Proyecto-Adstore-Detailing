package mindhub.adstoreDetailing.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.TurnoServicio;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class TurnoServicioDTO {
    private Long id;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private Set<CompraServicioDTO> compraServicios = new HashSet<>();

    public TurnoServicioDTO(TurnoServicio turnoServicio) {
        this.id = turnoServicio.getId();
        this.fechaHoraIngreso = turnoServicio.getFechaHoraIngreso();
        this.fechaHoraSalida = turnoServicio.getFechaHoraSalida();
        this.compraServicios = turnoServicio.getCompraServicios().stream().map(CompraServicioDTO::new).collect(Collectors.toSet());
    }
}
