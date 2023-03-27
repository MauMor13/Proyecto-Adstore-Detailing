package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.models.TarjetaAd;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

public class TarjetaAdDTO {
    private long id;
    private int numeroTarjeta;
    public TarjetaAdDTO(TarjetaAd tarjetaAd){
        this.id = tarjetaAd.getId();
        this.numeroTarjeta = tarjetaAd.getNumeroTarjeta();
    }
}
