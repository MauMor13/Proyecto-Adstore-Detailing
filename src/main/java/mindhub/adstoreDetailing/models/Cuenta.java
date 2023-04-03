package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuentaMiembro_seq")
    @SequenceGenerator(name = "cuentaMiembro_seq", sequenceName = "cuentaMiembro_id_seq", allocationSize = 1)
    private long id;
    private String numeroCuenta;
    private double saldo;
   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne(mappedBy = "cuenta", fetch = FetchType.EAGER)
    private TarjetaAd tarjetaAd;
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.EAGER)
    private Set<Compra> compras = new HashSet<>();
    public Cuenta(String numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }
    public void sumarCompra(Compra compra){
        compra.setCuenta(this);
        compras.add(compra);
    }
}