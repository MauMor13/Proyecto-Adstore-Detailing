package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraProducto;
import mindhub.adstoreDetailing.models.Producto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
public class CompraProductoDTO {
    private long id;
    private double precio;
    private double montoTotal;
    private int cantidad;
    private String nombreProducto;

    public CompraProductoDTO(CompraProducto compraProducto) {
        this.id = compraProducto.getId();
        this.precio = compraProducto.getPrecio();
        this.montoTotal = compraProducto.getMontoTotal();
        this.cantidad = compraProducto.getCantidad();
        this.nombreProducto = compraProducto.getProducto().getNombre();
    }
}
