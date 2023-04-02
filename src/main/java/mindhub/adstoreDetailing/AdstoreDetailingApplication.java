package mindhub.adstoreDetailing;
import mindhub.adstoreDetailing.models.*;
import mindhub.adstoreDetailing.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import static mindhub.adstoreDetailing.models.Categoria.*;
import static mindhub.adstoreDetailing.utilidades.Utilidad.*;

@SpringBootApplication
@EnableScheduling
public class AdstoreDetailingApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(AdstoreDetailingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(RepositorioCuenta repositorioCuenta, RepositorioTarjetaAd repositorioTarjetaAd, RepositorioCliente repositorioCliente, RepositorioProducto repositorioProducto, RepositorioServicio repositorioServicio, RepositorioCompraProducto repositorioCompraProducto, RepositorioCompraServicio repositorioCompraServicio, RepositorioCompra repositorioCompra) {
        return (args) -> {
            //crear cliente, cuenta y tarjeta
            Cliente cliente1 = new Cliente("Marcelo", "Rodriguez", "rioja 36 ,Cordoba,Argentina", "marcelo21@gmail.com", passwordEncoder.encode("marce12345"), "35245789");
            cliente1.setActivo(true);
            Cuenta cuentaCliente1 = new Cuenta(generarNumeroCuenta(repositorioCuenta), 50000);
            TarjetaAd tarjetaCliente1 = new TarjetaAd(generarNumeroTarjetaAd(repositorioTarjetaAd));

            repositorioCuenta.save(cuentaCliente1);
            repositorioTarjetaAd.save(tarjetaCliente1);
            repositorioCliente.save(cliente1);

            // establecer la relación entre Cliente y Cuenta
            cliente1.setCuenta(cuentaCliente1);
            cuentaCliente1.setCliente(cliente1);

            // establecer la relación entre Cuenta y TarjetaAd
            cuentaCliente1.setTarjetaAd(tarjetaCliente1);
            tarjetaCliente1.setCuenta(cuentaCliente1);


            //lista de productos
            Producto producto1 = new Producto("Guante de Lavado", 4650, 11, "Guante de lavado cómodo con agarre en la muñeca.Remueve fácil y suavemente suciedad, grasa y polvo sin generar rayas. Tamaño: 20,3cm x 25,4cm", "./assets/Imagenes-Productos/Guante_de_Lavado.png", ACCESORIOS);
            Producto producto2 = new Producto("Paño de Microfibra (Medium)", 2650, 12, "Paño de microfibra negro, ideal para la limpieza y lustrado de las  superficies del automóvil", "./assets/Imagenes-Productos/Paño_de_Microfibra.png", ACCESORIOS);
            Producto producto3 = new Producto("Esponja Aplicadora Multiuso", 4048, 20, "Esponja especial con lado de la empuñadura blanco y lado amarillo con microporos. De uso universal para aplicar y extender pulimentos,ceras y productos para la conservación de plásticos en el exterior del coche.", "./assets/Imagenes-Productos/Esponja_Aplicadora_Multiuso.png", ACCESORIOS);
            Producto producto4 = new Producto("Gold Class Leather Cleaner & Conditioner", 8200, 24, "Este tratamiento de un solo paso y PH balanceado protege y limpia de manera segura los cueros mas finos sin utilizar solventes ásperos o dañinos. Suave limpiadores proporcionan una acción de limpieza sin remover los nutrientes o la protección. Contenido Neto: 414 ml.", "./assets/Imagenes-Productos/Gold_Class_Leather_Clean.png", INTERIORES);
            Producto producto5 = new Producto("Quik Detailer Interior", 6650, 12, "La manera más rápida de limpiar y proteger todas las superficies internas. Mantiene la apariencia y la textura original de plásticos interiores, vinil, cueros, hules, metales y equipo de audio/video. Esta fórmula segura de alta lubricidad levanta la tierra, suciedad, huellas digitales sin dejar residuos.", "./assets/Imagenes-Productos/Ouik_Detailer_Interior.png", INTERIORES);
            Producto producto6 = new Producto("Gold Class Rich Leather Spray", 8200, 23, "Este tratamiento de un sólo paso y PH balanceado protege y limpia de manera segura los cueros más finos sin utilizar solventes ásperos o dañinos, una acción de limpieza sin remover los nutrientes o la protección.", "./assets/Imagenes-Productos/Gold_Class_Rich_Leath.png", INTERIORES);
            Producto producto7 = new Producto("Interior Cleaner Limpia Tapices", 9160, 12, "Elimina radicalmente y sin atacar las superficies, hasta las manchas más difíciles como grasa, aceite y manchas de alquitrán de revestimientos, cojines, tejidos, tejados de plástico y techos corredizos.", "./assets/Imagenes-Productos/Limpia_Tapizados_De_Int.png", INTERIORES);
            Producto producto8 = new Producto("Limpieza Interiores Mate Lemon Fresh", 7745, 12, "Limpia y cuida todas las partes en el interior del coche. Antiestatico, anti-polvo y protectivo. Confiere brillo y frescura para madea ", "./assets/Imagenes-Productos/Limpieza_Interiores_Mate.png", INTERIORES);
            Producto producto9 = new Producto("Loción Cuero", 6377, 15, "Loción de alta calidad para la limpieza delicada y el cuidado de interiores de coches, trajes de moto y otros tipos de cuero y piel lisa. Refresca los colores y la consistencia del cuero. Elimina sin esfuerzo suciedad, aceite y grasa. Deja el cuero especial mente flexible y lo previene de la quebradiza.", "./assets/Imagenes-Productos/Locion_Cuero.png", INTERIORES);
            Producto producto10 = new Producto("Ultimate Polish", 8850, 15, "Abrillantador que remueve los hologramas y pequeñas rayas, entregándole profundidad al color sin dañar la superficie de la pintura. Perfecto para eliminar marcas de remolinos finos,produciendo reflejos profundos y brillos intensos.", "./assets/Imagenes-Productos/Ultimate_Polish.png", PULIDO);
            Producto producto11 = new Producto("Ultimate Fast Finish", 15600, 15, "Es una revolucionaria fórmula en aerosol que ofrece máximo brillo con un revestimiento de polímero sintético de larga duración (hasta un año). Protege por meses y deja excelente repelencia al agua, incluye microfibra", "./assets/Imagenes-Productos/Ultimate_Fast_Finish.png", PULIDO);
            Producto producto12 = new Producto("Hybrid Ceramic Wash & Was", 15660, 15, "Tecnología propia de Meguiar's para una experiencia unica de lavado y encerado. Crea una durable y extrema repelencia al agua, deja la pintura del auto super suave, y el secado se hace fácil ya que el agua se desvanece rápidamente.", "./assets/Imagenes-Productos/Hybrid_Ceramic_Wash.png", PULIDO);
            Producto producto13 = new Producto("Profiline Final 1-6 1lt", 21476, 20, "Pulimento suave de alto brillo con sellado rápido para uso manual y mecánico. Genera colores intensos y ricos para un perfecto acabado de sala de exposición y una intensa suavidad de superficie. Elimina micro-rayones en pintura nueva o previamente pulida.", "./assets/Imagenes-Productos/Profiline_Final.png", PULIDO);
            Producto producto14 = new Producto("Xtreme Ceramic Quick Detail 750ml", 14806, 18, "El detallador rápido de cerámica XTREME asegura una suavidad extrema de la laca en muy poco tiempo y deja con un brillo similar a un espejo. La suciedad superficial ligera como el polvo, las huellas dactilares, etc. se pueden eliminar rápidamente y sin rayas. Refresca las juntas cerámicas existentes y deja un efecto de abalorios.", "./assets/Imagenes-Productos/Xtreme_Ceramic_Quick.png", PULIDO);
            Producto producto15 = new Producto("Cera Easy Shine", 6141, 12, "La fórmula avanzada permite que se aplique a pleno sol o sobre carrocería caliente. Es extremadamente fácil de aplicar y deja un brillo deslumbrante y duradero. Limpia, pule y protege en un una sola operación. Recomendada para proteger pinturas nuevas o renovar pinturas decoloradas.", "./assets/Imagenes-Productos/Cera_Easy_Shine.png", PULIDO);
            Producto producto16 = new Producto("Gold Class Shampoo & Conditioner Car Wash", 12000, 16, "Es un producto diseñado tanto para limpiar como para acondicionar la pintura en un solo paso. La fórmula premium espuma suavemente la suciedad y contaminantes sin comprometer la protección de la cera.", "./assets/Imagenes-Productos/Gold_Class_Shampoo_Cond.png", LAVADO);
            Producto producto17 = new Producto("Soft Wash Gel", 5200, 30, "Super espesor y super concentrado, esta increíble formula combina ricos acondicionadores con abrillantadores ópticos para resaltar el brillo y los reflejos de su pintura. No contiene detergentes y tiene un PH balanceado, no removerá la protección de la cera.", "./assets/Imagenes-Productos/Soft_Wash_Gel.png", LAVADO);
            Producto producto18 = new Producto("NXT Generation Car Wash", 13600, 15, "Shampoo con ph 0. La tierra y la suciedad son aflojadas y removidas mientras que la protección de la cera se mantiene. Nuestra formulación más popular en un empaque de tamaño económico.", "./assets/Imagenes-Productos/NXT_Generation_Car.png", LAVADO);
            Producto producto19 = new Producto("Xtreme Shampoo 2 en 1 Wash & Dry 1lt", 9833, 12, "Shampoo con prosecante para el lavado a mano del automóvil. Para limpieza de superficies pintadas, metales, goma y plástico. Lavar sin secar!", "./assets/Imagenes-Productos/Xtreme_Shampoo.png", LAVADO);
            Producto producto20 = new Producto("Profiline Shampoo Actifoam Energy 1lt", 14082, 5, "Limpiador fuerte que disuelve la suciedad con mucha espuma para el lavado del vehículo con rociador de espuma. Perfecto como shampoo, eliminador de insectos y limpiador de llantas. Concentrado, para al menos 50 coches. De aroma agradable, ph neutro y la mejor compatibilidad con materiales, también para superficies con láminas adheridas.", "./assets/Imagenes-Productos/Profiline_Shampoo_Actifoam.png", LAVADO);
            Producto producto21 = new Producto("Shampoo Concentradi Ph Neutro 2lt", 7107, 0, "Limpia cuidadosamente y elimina detenidamente todo tipo de suciedad. Apropiado para la limpieza de superficies pintadas, metal, vidrio, plástico, goma, baldosas, porcelana y esmalte.", "./assets/Imagenes-Productos/Shampoo_Concentrado.png", LAVADO);
            //lista de sevicios
            Servicio servicio1 = new Servicio("Lavado Basico", "Detallado de interior, aspirado al detalle, lavado de carrocería al detalle, terminación con cera en spray", 5000, Duration.ofMinutes(90), "./assets/Imagenes/lavadoBasico.png");
            Servicio servicio2 = new Servicio("Servicio Premium de Lavado", "Interior detallado con un acondicionador para los plásticos, aspirado al detalle, acondicionado de alfombras de goma. Lavado de carrocería y llantas al detalle con productos ferricos, para una mayor limpieza con una terminación de cera en pasta", 7000, Duration.ofMinutes(120), "./assets/Imagenes/lavadoPremium.png");
            Servicio servicio3 = new Servicio("Tratamiento Ceramico", "Brindamos distintos tipos de tratamientos cerámicos que varían solo en el sellador a utilizar, con una durabilidad de entre 3 a 5 años. El sellador le da una protección a la laca del vehículo, con esto los lavados serán más duraderos y no se le pegara tanto la tierra. También podrás observar cuando me moje las gotas de agua estarán bien definidas y de deslizan mas rápido. A continuación se le detallará el proceso para la realización", 9000, Duration.ofMinutes(180), "./assets/Imagenes/ceramico.png");
            Servicio servicio4 = new Servicio("Tratamiento Acrilico","Con nuestro Tratamiento Acrílico, rejuvenecé tu vehículo y mantenelo limpio, brillante y protegido en todo momento, consiste en la aplicación de una película protectora a base de acrílico, que permitirá un proceso de descontaminación de la pintura, seguido por un sistema de pulido, lustre, abrillantado, encerado y sellado con Cera de Carnauba, dando como resultado un acabado final de excelencia",15000,Duration.ofMinutes(180),"./assets/Imagenes/acrilico.png");
            //guardado en repositorio

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
                //guardado de servicios
            repositorioServicio.save(servicio1);
            repositorioServicio.save(servicio2);
            repositorioServicio.save(servicio3);
            repositorioServicio.save(servicio4);

            //crear compra
            Compra compraCliente1 = new Compra(12000, LocalDateTime.now(), 0);
            repositorioCompra.save(compraCliente1);
            cuentaCliente1.sumarCompra(compraCliente1);

            crearCompraPoducto(compraCliente1, new ArrayList<Producto>(Arrays.asList(producto3, producto4, producto7, producto2)), repositorioCompraProducto, repositorioCompra);
            crearCompraServicio(compraCliente1, new ArrayList<Servicio>(Arrays.asList(servicio1, servicio2)), repositorioCompraServicio, repositorioCompra);

             //guardar las entidades en la base de datos
            repositorioCuenta.save(cuentaCliente1);
            repositorioTarjetaAd.save(tarjetaCliente1);
            repositorioCliente.save(cliente1);
            repositorioCompra.save(compraCliente1);

            Cliente admin = new Cliente("admin", "admin", "admin", "admin@storage.com", "5555", "5555");
            admin.setActivo(true);
            Cuenta cuentaAdmin = new Cuenta(generarNumeroCuenta(repositorioCuenta), 0);
            TarjetaAd tarjetaAdmin = new TarjetaAd(generarNumeroTarjetaAd(repositorioTarjetaAd));
            repositorioCuenta.save(cuentaAdmin);
            repositorioTarjetaAd.save(tarjetaAdmin);
            repositorioCliente.save(admin);
            // establecer la relación entre Cliente y Cuenta
            admin.setCuenta(cuentaAdmin);
            cuentaAdmin.setCliente(admin);

            // establecer la relación entre Cuenta y TarjetaAd
            cuentaAdmin.setTarjetaAd(tarjetaAdmin);
            tarjetaAdmin.setCuenta(cuentaAdmin);
            repositorioCuenta.save(cuentaAdmin);
            repositorioTarjetaAd.save(tarjetaAdmin);
            repositorioCliente.save(admin);
        };
    }
}