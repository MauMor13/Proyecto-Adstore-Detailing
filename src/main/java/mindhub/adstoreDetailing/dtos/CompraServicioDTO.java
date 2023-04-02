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
    public CompraServicioDTO(CompraServicio compraServicio){
        this.id= compraServicio.getId();
        this.precio= compraServicio.getPrecio();
    }
}
