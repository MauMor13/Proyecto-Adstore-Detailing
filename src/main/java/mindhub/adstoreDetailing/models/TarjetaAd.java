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
public class TarjetaAd {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarjetaMiembro_seq")
    @SequenceGenerator(name = "tarjetaMiembro_seq", sequenceName = "tarjetaMiembro_id_seq", allocationSize = 1)
    private long id;
    private int numeroTarjeta;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cuenta_id")
    private Cuenta cuenta;
    public TarjetaAd(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}