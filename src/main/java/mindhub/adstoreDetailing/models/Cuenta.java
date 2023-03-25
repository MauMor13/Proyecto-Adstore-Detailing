package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuentaMiembro_seq")
    @SequenceGenerator(name = "cuentaMiembro_seq", sequenceName = "cuentaMiembro_id_seq", allocationSize = 1)
    private long id;
    private String numeroCuenta;
    private double saldo;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne(mappedBy = "cuenta")
    private TarjetaAd tarjetaAd;
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.EAGER)
    Set<Compra> compras = new HashSet<>();
}