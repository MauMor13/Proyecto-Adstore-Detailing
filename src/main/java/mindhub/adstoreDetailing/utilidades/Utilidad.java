package mindhub.adstoreDetailing.utilidades;
import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioTarjetaAd;

public class Utilidad {
    public static String generarNumeroCuenta(RepositorioCuenta repositorioCuenta){
        String numero;
        boolean verificarNumero;
        do {
            numero = numeroAleatorioCuenta();
            verificarNumero = repositorioCuenta.existsCuentaByNumeroCuenta(numero);
        }while(verificarNumero);
        return numero;
    }
    public static String numeroAleatorioCuenta(){
        int numero1 = (int) (Math.random() * (99999999));
        return "VIN-"+numero1;
    }
    public static int generarNumeroTarjetaAd(RepositorioTarjetaAd repositorioTarjetaAd){
        int numeroTarjeta;
        boolean verificarNumero;
        do {
            numeroTarjeta = numeroAleatorioTarjeta();
            verificarNumero = repositorioTarjetaAd.existsTarjetaByNumeroTarjeta(numeroTarjeta);
        }while (verificarNumero);
        return numeroTarjeta;
    }
    public static int numeroAleatorioTarjeta(){
        return (int) (Math.random() * (99999));
    }
}
