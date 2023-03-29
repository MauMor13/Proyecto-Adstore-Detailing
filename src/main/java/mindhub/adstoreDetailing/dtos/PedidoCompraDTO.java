package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class PedidoCompraDTO {
    private List<Arrays> productos;
    private List<Arrays> servicios;
    private LocalDateTime fechaServicio;

    public PedidoCompraDTO(List<Arrays> productos, List<Arrays> servicios, LocalDateTime fechaServicio) {
        this.productos = productos;
        this.servicios = servicios;
        this.fechaServicio = fechaServicio;
    }
}
