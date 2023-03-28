package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraServicio;
import mindhub.adstoreDetailing.models.Servicio;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class CompraServicioDTO {
    private Long id;
    private Double precio;
    private LocalDateTime fechaReserva;
    public CompraServicioDTO(CompraServicio compraServicio){
        this.id= compraServicio.getId();
        this.precio= compraServicio.getPrecio();
        this.fechaReserva=compraServicio.getFechaReserva();
    }
}
