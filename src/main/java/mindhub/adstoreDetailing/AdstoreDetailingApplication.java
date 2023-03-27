package mindhub.adstoreDetailing;

import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static mindhub.adstoreDetailing.models.Categoria.ACCESORIOS;
import static mindhub.adstoreDetailing.models.Categoria.INTERIORES;
import static mindhub.adstoreDetailing.utilidades.Utilidad.GenerarNumeroCuenta;
import static mindhub.adstoreDetailing.utilidades.Utilidad.GenerarNumeroTarjetaAd;

@SpringBootApplication
public class AdstoreDetailingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(AdstoreDetailingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(RepositorioCliente repositorioCliente,
									  RepositorioCuenta repositorioCuenta,
									  RepositorioTarjetaAd repositorioTarjetaAd,
									  RepositorioProducto repositorioProducto,
									  RepositorioServicio repositorioServicio,
									  RepositorioCompraProducto repositorioCompraProducto,
									  RepositorioCompraServicio repositorioCompraServicio) {
		return (args) -> {
			//crear cliente, cuenta y tarjeta
		Cliente cliente1 = new Cliente("Marcelo","Rodriguez","rioja 36 ,Cordoba,Argentina","marcelo21@gmail.com","marce12345",35245789);
		Cuenta cuentaCliente1 = new Cuenta(GenerarNumeroCuenta(repositorioCuenta),50000);
		TarjetaAd tarjetaCliente1 = new TarjetaAd(GenerarNumeroTarjetaAd(repositorioTarjetaAd));

			//lista de productos
		Producto producto1 = new Producto("Guante de Lavado",4650,11, "Guante de lavado cómodo con agarre en la muñeca.Remueve fácil y suavemente suciedad, grasa y polvo sin generar rayas. Tamaño: 20,3cm x 25,4cm", "imagen", ACCESORIOS);
		Producto producto2 = new Producto("Paño de Microfibra (Medium)",2650,12,"Paño de microfibra negro, ideal para la limpieza y lustrado de las  superficies del automóvil","imagen",ACCESORIOS);
		Producto producto3 = new Producto("Esponja Aplicadora Multiuso",4048,20,"Esponja especial con lado de la empuñadura blanco y lado amarillo con microporos. De uso universal para aplicar y extender pulimentos,ceras y productos para la conservación de plásticos en el exterior del coche.","imagen",ACCESORIOS);
		Producto producto4 = new Producto("Gold Class Leather Cleaner & Conditioner",8200,24,"Este tratamiento de un solo paso y PH balanceado protege y limpia de manera segura los cueros mas finos sin utilizar solventes ásperos o dañinos. Suave limpiadores proporcionan una acción de limpieza sin remover los nutrientes o la protección. Contenido Neto: 414 ml.","imagen",INTERIORES);
		Producto producto5 = new Producto("Quik Detailer Interior",6650,12,"La manera más rápida de limpiar y proteger todas las superficies internas. Mantiene la apariencia y la textura original de plásticos interiores, vinil, cueros, hules, metales y equipo de audio/video. Esta fórmula segura de alta lubricidad levanta la tierra, suciedad, huellas digitales sin dejar residuos.","imagen",INTERIORES);
		Producto producto6 = new Producto("Gold Class Rich Leather Spray",8200,23,"Este tratamiento de un sólo paso y PH balanceado protege y limpia de manera segura los cueros más finos sin utilizar solventes ásperos o dañinos, una acción de limpieza sin remover los nutrientes o la protección.","imagen",INTERIORES);
		Producto producto7 = new Producto("Interior Cleaner Limpia Tapices",9160,12,"Elimina radicalmente y sin atacar las superficies, hasta las manchas más difíciles como grasa, aceite y manchas de alquitrán de revestimientos, cojines, tejidos, tejados de plástico y techos corredizos.","imagen",INTERIORES);
		Producto producto8 = new Producto("Limpieza Interiores Mate Lemon Fresh",7745,12,"Limpia y cuida todas las partes en el interior del coche. Antiestatico, anti-polvo y protectivo. Confiere brillo y frescura para madea ","imagen",INTERIORES);
		Producto producto9 = new Producto(,INTERIORES);
		Producto producto10 = new Producto();
		Producto producto11 = new Producto();
		Producto producto12 = new Producto();
		Producto producto13 = new Producto();
		Producto producto14 = new Producto();
		Producto producto15 = new Producto();
		Producto producto16 = new Producto();
		Producto producto17 = new Producto();
		Producto producto18 = new Producto();
		Producto producto19 = new Producto();
		Producto producto20 = new Producto();
		Producto producto21 = new Producto();

		};
	}
}