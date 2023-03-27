package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq")
    @SequenceGenerator(name = "compra_seq", sequenceName = "compra_id_seq", allocationSize = 1)
    private long id;
    private double montoFinal;
    private LocalDateTime fecha;
    private int descuento;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    @OneToMany(mappedBy = "compra",fetch = FetchType.EAGER)
    private Set<CompraProducto> compraProducto = new HashSet<>();
    @OneToMany(mappedBy = "compra",fetch = FetchType.EAGER)
    private Set<CompraServicio> compraServicio = new HashSet<>();

    public Compra(double montoFinal, LocalDateTime fecha, int descuento) {
        this.montoFinal = montoFinal;
        this.fecha = fecha;
        this.descuento = descuento;
    }
}
