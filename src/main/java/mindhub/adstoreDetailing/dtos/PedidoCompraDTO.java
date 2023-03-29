package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PedidoCompraDTO {
    private List<Long> productos;
    private List<Long> servicios;
    private LocalDateTime fechaServicio;

    public PedidoCompraDTO(List<Long> productos, List<Long> servicios, LocalDateTime fechaServicio) {
        this.productos = productos;
        this.servicios = servicios;
        this.fechaServicio = fechaServicio;
    }
}
