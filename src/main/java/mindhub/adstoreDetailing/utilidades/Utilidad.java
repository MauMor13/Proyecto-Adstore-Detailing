package mindhub.adstoreDetailing.utilidades;

import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioTarjetaAd;

public class Utilidad {
    public static String GenerarNumeroCuenta(RepositorioCuenta repositorioCuenta){
        String numero;
        boolean verificarNumero;
        do {
            numero = NumeroAleatorioCuenta();
            verificarNumero = repositorioCuenta.existsCuentaByNumero(numero);
        }while(verificarNumero);
        return numero;
    }
    public static String NumeroAleatorioCuenta(){
        int numero1 = (int) (Math.random() * (99999999));
        return "VIN-"+numero1;
    }
    public static int GenerarNumeroTarjetaAd(RepositorioTarjetaAd repositorioTarjetaAd){
        int numeroTarjeta;
        boolean verificarNumero;
        do {
            numeroTarjeta = NumeroAleatorioTarjeta();
            verificarNumero = repositorioTarjetaAd.existsTarjetaByNumero(numeroTarjeta);
        }while (verificarNumero);
        return numeroTarjeta;
    }
    public static int NumeroAleatorioTarjeta(){
        return (int) (Math.random() * (99999));
    }
}
