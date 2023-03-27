package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraProducto;
import mindhub.adstoreDetailing.models.Producto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CompraProductoDTO {
    private long id;
    private double precio;
    private double montoTotal;
    private int cantidad;
    private Compra compra;
    private Producto producto;
    public CompraProductoDTO(CompraProducto compraProducto){
        this.id=compraProducto.getId();
        this.precio= compraProducto.getPrecio();
        this.montoTotal= compraProducto.getMontoTotal();
        this.cantidad= compraProducto.getCantidad();
        this.producto=compraProducto.getProducto();
    }
}
