package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.CompraProducto;
import mindhub.adstoreDetailing.models.CompraServicio;
import mindhub.adstoreDetailing.models.Cuenta;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CompraDTO {
    private long id;
    private double montoFinal;
    private LocalDateTime fecha;
    private int descuento;
  //  private Cuenta cuenta;
    private Set<CompraProductoDTO> compraProducto;
    private Set<CompraServicioDTO> compraServicio;
    public CompraDTO(Compra compra){
        this.id=compra.getId();
        this.montoFinal= compra.getMontoFinal();
        this.fecha=compra.getFecha();
        this.descuento= compra.getDescuento();
        this.compraProducto=compra.getCompraProducto().stream().map(CompraProductoDTO::new).collect(Collectors.toSet());
        this.compraServicio=compra.getCompraServicio().stream().map(CompraServicioDTO::new).collect(Collectors.toSet());
    }
}