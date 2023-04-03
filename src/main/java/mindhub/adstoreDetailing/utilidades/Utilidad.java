package mindhub.adstoreDetailing.utilidades;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
    public static void crearCompraPoducto(Compra compra, List<Producto> producto, RepositorioCompraProducto repositorioCompraProducto, RepositorioCompra repositorioCompra){
        for (int i=0;i<producto.size();i++){
            CompraProducto nuevaCompraProducto = new CompraProducto(compra, producto.get(i),1);
            repositorioCompraProducto.save(nuevaCompraProducto);
            repositorioCompra.save(compra);
        }
    }
    public static void crearCompraServicio(Compra compra, List<Servicio> servicio, RepositorioCompraServicio repositorioCompraServicio,RepositorioCompra repositorioCompra){
        for (int i=0;i<servicio.size();i++){
            CompraServicio nuevaCompraServicio = new CompraServicio(compra, servicio.get(i));
            repositorioCompraServicio.save(nuevaCompraServicio);
            repositorioCompra.save(compra);
        }
    }
    private static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
