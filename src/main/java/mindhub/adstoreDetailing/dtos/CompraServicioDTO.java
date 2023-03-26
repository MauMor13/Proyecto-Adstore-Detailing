package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraServicio;
import mindhub.adstoreDetailing.models.Servicio;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class CompraServicioDTO {
    private Long id;
    private Double precio;
    private Integer cantidad;
    private double montoTotal;
    private LocalDateTime fechaReserva;
    private Compra compra;
    private Servicio servicio;
    public CompraServicioDTO(CompraServicio compraServicio){
        this.id= compraServicio.getId();
        this.precio= compraServicio.getPrecio();
        this.cantidad= compraServicio.getCantidad();
        this.montoTotal= compraServicio.getMontoTotal();
        this.fechaReserva=compraServicio.getFechaReserva();
        this.compra=compraServicio.getCompra();
        this.servicio=compraServicio.getServicio();
    }
}
