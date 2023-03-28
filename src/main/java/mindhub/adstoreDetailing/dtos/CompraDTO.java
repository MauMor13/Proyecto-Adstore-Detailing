package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Compra;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CompraDTO {
    private long id;
    private double montoFinal;
    private LocalDateTime fecha;
    private int descuento;
    private Set<CompraProductoDTO> compraProducto;
    private Set<CompraServicioDTO> compraServicio;

    public CompraDTO(Compra compra) {
        this.id = compra.getId();
        this.montoFinal = compra.getMontoFinal();
        this.fecha = compra.getFecha();
        this.descuento = compra.getDescuento();
        this.compraProducto = compra.getCompraProductos().stream().map(CompraProductoDTO::new).collect(Collectors.toSet());
        this.compraServicio = compra.getCompraServicios().stream().map(CompraServicioDTO::new).collect(Collectors.toSet());
    }
}