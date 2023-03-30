package mindhub.adstoreDetailing.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PedidoProductoDTO {
    private long id;
    private int cantidad;

    public PedidoProductoDTO(long id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }
}
