package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.models.TarjetaAd;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CuentaDTO {
    private long id;
    private String numeroCuenta;
    private double saldo;
    private TarjetaAdDTO tarjetaAd;
    private Set<CompraDTO> compras;

    public CuentaDTO(Cuenta cuenta) {
        this.id = cuenta.getId();
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.saldo = cuenta.getSaldo();
        this.tarjetaAd = new TarjetaAdDTO(cuenta.getTarjetaAd());
        this.compras = cuenta.getCompras().stream().map(CompraDTO::new).collect(Collectors.toSet());
    }
}