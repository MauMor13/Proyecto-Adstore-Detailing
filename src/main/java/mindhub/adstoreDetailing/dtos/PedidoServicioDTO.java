package mindhub.adstoreDetailing.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoServicioDTO {
    private long id;

    public PedidoServicioDTO(long id) {
        this.id = id;
    }
}
