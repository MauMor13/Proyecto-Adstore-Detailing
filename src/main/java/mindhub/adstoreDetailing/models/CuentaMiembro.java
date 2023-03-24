package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CuentaMiembro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuentaMiembro_seq")
    @SequenceGenerator(name = "cuentaMiembro_seq", sequenceName = "cuentaMiembro_id_seq", allocationSize = 1)
    private long id;
    private String numeroCuenta;
    private double saldo;
    private int puntosCompra;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne(mappedBy = "cuentaMiembro")
    private TarjetaMiembro tarjetaMiembro;
}