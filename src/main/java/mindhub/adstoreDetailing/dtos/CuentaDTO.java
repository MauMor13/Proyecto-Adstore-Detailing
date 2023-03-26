package mindhub.adstoreDetailing.dtos;

import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.models.TarjetaAd;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CuentaDTO {
    private long id;
    private String numeroCuenta;
    private double saldo;
    private TarjetaAd tarjetaAd;
    private Set<CompraDTO> compras;
    public CuentaDTO(Cuenta cuenta){
        this.id= cuenta.getId();
        this.numeroCuenta= cuenta.getNumeroCuenta();
        this.saldo= cuenta.getSaldo();
        this.tarjetaAd=cuenta.getTarjetaAd();
        this.compras=cuenta.getCompras().stream().map(CompraDTO::new).collect(Collectors.toSet());
    }
}