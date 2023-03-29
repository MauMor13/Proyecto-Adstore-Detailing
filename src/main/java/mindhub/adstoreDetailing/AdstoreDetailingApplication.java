package mindhub.adstoreDetailing;

import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

import static mindhub.adstoreDetailing.models.Categoria.*;
import static mindhub.adstoreDetailing.utilidades.Utilidad.generarNumeroCuenta;
import static mindhub.adstoreDetailing.utilidades.Utilidad.generarNumeroTarjetaAd;

@SpringBootApplication
public class AdstoreDetailingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(AdstoreDetailingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(RepositorioCuenta repositorioCuenta,
									  RepositorioTarjetaAd repositorioTarjetaAd,
									  RepositorioCliente repositorioCliente,
									  RepositorioProducto repositorioProducto,
									  RepositorioServicio repositorioServicio) {
		return (args) -> {
			//crear cliente, cuenta y tarjeta
		Cliente cliente1 = new Cliente("Marcelo","Rodriguez","rioja 36 ,Cordoba,Argentina","marcelo21@gmail.com","marce12345",35245789);
		Cuenta cuentaCliente1 = new Cuenta(generarNumeroCuenta(repositorioCuenta),50000);
		TarjetaAd tarjetaCliente1 = new TarjetaAd(generarNumeroTarjetaAd(repositorioTarjetaAd));
//		 establecer la relación entre Cliente y Cuenta
		cliente1.setCuenta(cuentaCliente1);
		cuentaCliente1.setCliente(cliente1);
//
//		 establecer la relación entre Cuenta y TarjetaAd
		cuentaCliente1.setTarjetaAd(tarjetaCliente1);
		tarjetaCliente1.setCuenta(cuentaCliente1);
//
//		 guardar las entidades en la base de datos
//			lista de productos
		Producto producto1 = new Producto("Guante de Lavado",4650,11, "Guante de lavado cómodo con agarre en la muñeca.Remueve fácil y suavemente suciedad, grasa y polvo sin generar rayas. Tamaño: 20,3cm x 25,4cm", "imagen", ACCESORIOS);
		Producto producto2 = new Producto("Paño de Microfibra (Medium)",2650,12,"Paño de microfibra negro, ideal para la limpieza y lustrado de las  superficies del automóvil","imagen",ACCESORIOS);
		Producto producto3 = new Producto("Esponja Aplicadora Multiuso",4048,20,"Esponja especial con lado de la empuñadura blanco y lado amarillo con microporos. De uso universal para aplicar y extender pulimentos,ceras y productos para la conservación de plásticos en el exterior del coche.","imagen",ACCESORIOS);
		Producto producto4 = new Producto("Gold Class Leather Cleaner & Conditioner",8200,24,"Este tratamiento de un solo paso y PH balanceado protege y limpia de manera segura los cueros mas finos sin utilizar solventes ásperos o dañinos. Suave limpiadores proporcionan una acción de limpieza sin remover los nutrientes o la protección. Contenido Neto: 414 ml.","imagen",INTERIORES);
		Producto producto5 = new Producto("Quik Detailer Interior",6650,12,"La manera más rápida de limpiar y proteger todas las superficies internas. Mantiene la apariencia y la textura original de plásticos interiores, vinil, cueros, hules, metales y equipo de audio/video. Esta fórmula segura de alta lubricidad levanta la tierra, suciedad, huellas digitales sin dejar residuos.","imagen",INTERIORES);
		Producto producto6 = new Producto("Gold Class Rich Leather Spray",8200,23,"Este tratamiento de un sólo paso y PH balanceado protege y limpia de manera segura los cueros más finos sin utilizar solventes ásperos o dañinos, una acción de limpieza sin remover los nutrientes o la protección.","imagen",INTERIORES);
		Producto producto7 = new Producto("Interior Cleaner Limpia Tapices",9160,12,"Elimina radicalmente y sin atacar las superficies, hasta las manchas más difíciles como grasa, aceite y manchas de alquitrán de revestimientos, cojines, tejidos, tejados de plástico y techos corredizos.","imagen",INTERIORES);
		Producto producto8 = new Producto("Limpieza Interiores Mate Lemon Fresh",7745,12,"Limpia y cuida todas las partes en el interior del coche. Antiestatico, anti-polvo y protectivo. Confiere brillo y frescura para madea ","imagen",INTERIORES);
		Producto producto9 = new Producto("Loción Cuero",6377,15,"Loción de alta calidad para la limpieza delicada y el cuidado de interiores de coches, trajes de moto y otros tipos de cuero y piel lisa. Refresca los colores y la consistencia del cuero. Elimina sin esfuerzo suciedad, aceite y grasa. Deja el cuero especial mente flexible y lo previene de la quebradiza.","imagen",INTERIORES);
		Producto producto10 = new Producto("Ultimate Polish",8850,15,"Abrillantador que remueve los hologramas y pequeñas rayas, entregándole profundidad al color sin dañar la superficie de la pintura. Perfecto para eliminar marcas de remolinos finos,produciendo reflejos profundos y brillos intensos.","imagen",PULIDO);
		Producto producto11 = new Producto("Ultimate Fast Finish",15600,15,"Es una revolucionaria fórmula en aerosol que ofrece máximo brillo con un revestimiento de polímero sintético de larga duración (hasta un año). Protege por meses y deja excelente repelencia al agua, incluye microfibra","imagen",PULIDO);
		Producto producto12 = new Producto("Hybrid Ceramic Wash & Was",15660,15,"Tecnología propia de Meguiar's para una experiencia unica de lavado y encerado. Crea una durable y extrema repelencia al agua, deja la pintura del auto super suave, y el secado se hace fácil ya que el agua se desvanece rápidamente.","imagen",PULIDO);
		Producto producto13 = new Producto("Profiline Final 1-6 1lt",21476,20,"Pulimento suave de alto brillo con sellado rápido para uso manual y mecánico. Genera colores intensos y ricos para un perfecto acabado de sala de exposición y una intensa suavidad de superficie. Elimina micro-rayones en pintura nueva o previamente pulida.","imagen",PULIDO);
		Producto producto14 = new Producto("Xtreme Ceramic Quick Detail 750ml",14806,18,"El detallador rápido de cerámica XTREME asegura una suavidad extrema de la laca en muy poco tiempo y deja con un brillo similar a un espejo. La suciedad superficial ligera como el polvo, las huellas dactilares, etc. se pueden eliminar rápidamente y sin rayas. Refresca las juntas cerámicas existentes y deja un efecto de abalorios.","imagen",PULIDO);
		Producto producto15 = new Producto("Cera Easy Shine",6141,12,"La fórmula avanzada permite que se aplique a pleno sol o sobre carrocería caliente. Es extremadamente fácil de aplicar y deja un brillo deslumbrante y duradero. Limpia, pule y protege en un una sola operación. Recomendada para proteger pinturas nuevas o renovar pinturas decoloradas.","imagen",PULIDO);
		Producto producto16 = new Producto("Gold Class Shampoo & Conditioner Car Wash",12000,16,"Es un producto diseñado tanto para limpiar como para acondicionar la pintura en un solo paso. La fórmula premium espuma suavemente la suciedad y contaminantes sin comprometer la protección de la cera.","imagen",LAVADO);
		Producto producto17 = new Producto("Soft Wash Gel",5200,30,"Super espesor y super concentrado, esta increíble formula combina ricos acondicionadores con abrillantadores ópticos para resaltar el brillo y los reflejos de su pintura. No contiene detergentes y tiene un PH balanceado, no removerá la protección de la cera.","imagen",LAVADO);
		Producto producto18 = new Producto("NXT Generation Car Wash",13600,15,"Shampoo con ph 0. La tierra y la suciedad son aflojadas y removidas mientras que la protección de la cera se mantiene. Nuestra formulación más popular en un empaque de tamaño económico.","imagen",LAVADO);
		Producto producto19 = new Producto("Xtreme Shampoo 2 en 1 Wash & Dry 1lt",9833,12,"Shampoo con prosecante para el lavado a mano del automóvil. Para limpieza de superficies pintadas, metales, goma y plástico. Lavar sin secar!","imagen",LAVADO);
		Producto producto20 = new Producto("Profiline Shampoo Actifoam Energy 1lt",14082,18,"Limpiador fuerte que disuelve la suciedad con mucha espuma para el lavado del vehículo con rociador de espuma. Perfecto como shampoo, eliminador de insectos y limpiador de llantas. Concentrado, para al menos 50 coches. De aroma agradable, ph neutro y la mejor compatibilidad con materiales, también para superficies con láminas adheridas.","imagen",LAVADO);
		Producto producto21 = new Producto("Shampoo Concentradi Ph Neutro 2lt",7107,19,"Limpia cuidadosamente y elimina detenidamente todo tipo de suciedad. Apropiado para la limpieza de superficies pintadas, metal, vidrio, plástico, goma, baldosas, porcelana y esmalte.","imagen",LAVADO);
//			lista de sevicios
		Servicio servicio1 = new Servicio("Lavado Basico","Detallado de interior, aspirado al detalle, lavado de carrocería al detalle, terminación con cera en spray.",5000, Duration.ofMinutes(90),"imagen");
		Servicio servicio2 = new Servicio("Servicio premium de lavado","Interior detallado con un acondicionador para los plásticos, aspirado al detalle, acondicionado de alfombras de goma. Lavado de carrocería y llantas al detalle con productos ferricos, para una mayor limpieza con una terminación de cera en pasta",7000,Duration.ofMinutes(120),"imagen");
		Servicio servicio3 = new Servicio("Tratamiento cerramiento","Brindamos distintos tipos de tratamientos cerámicos que varían solo en el sellador a utilizar, con una durabilidad de entre 3 a 5 años. El sellador le da una protección a la laca del vehículo, con esto los lavados serán más duraderos y no se le pegara tanto la tierra. También podrás observar cuando me moje las gotas de agua estarán bien definidas y de deslizan mas rápido. A continuación se le detallará el proceso para la realización.",9000,Duration.ofMinutes(180),"imagen");
//			crear compra
		Compra compraCliente1 = new Compra();

//		guardado en repositorio

		repositorioProducto.save(producto1);
		repositorioProducto.save(producto2);
		repositorioProducto.save(producto3);
		repositorioProducto.save(producto4);
		repositorioProducto.save(producto5);
		repositorioProducto.save(producto6);
		repositorioProducto.save(producto7);
		repositorioProducto.save(producto8);
		repositorioProducto.save(producto9);
		repositorioProducto.save(producto10);
		repositorioProducto.save(producto11);
		repositorioProducto.save(producto12);
		repositorioProducto.save(producto13);
		repositorioProducto.save(producto14);
		repositorioProducto.save(producto15);
		repositorioProducto.save(producto16);
		repositorioProducto.save(producto17);
		repositorioProducto.save(producto18);
		repositorioProducto.save(producto19);
		repositorioProducto.save(producto20);
		repositorioProducto.save(producto21);
		repositorioServicio.save(servicio1);
		repositorioServicio.save(servicio2);
		repositorioServicio.save(servicio3);
		repositorioCliente.save(cliente1);
		repositorioCuenta.save(cuentaCliente1);
		repositorioTarjetaAd.save(tarjetaCliente1);
		};
	}
}