package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_seq")
    @SequenceGenerator(name = "compra_seq", sequenceName = "compra_id_seq", allocationSize = 1)
    private long id;
    private double montoTotal;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Cliente comprador;

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    Set<CompraProducto> compraProductos;

    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    Set<CompraServicio> compraServicios;

}
