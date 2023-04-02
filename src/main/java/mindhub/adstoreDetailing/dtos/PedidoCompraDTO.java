package mindhub.adstoreDetailing.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PedidoCompraDTO {
    private List<PedidoProductoDTO> productos;
    private List<PedidoServicioDTO> servicios;
    private LocalDateTime fechaServicio;

    public PedidoCompraDTO(List<PedidoProductoDTO> productos, List<PedidoServicioDTO> servicios, LocalDateTime fechaServicio) {
        this.productos = productos;
        this.servicios = servicios;
        this.fechaServicio = fechaServicio;
    }
}
