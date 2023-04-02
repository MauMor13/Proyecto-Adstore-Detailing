package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.CompraDTO;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraProducto;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.models.Servicio;
import mindhub.adstoreDetailing.repositorios.RepositorioProducto;
import mindhub.adstoreDetailing.repositorios.RepositorioServicio;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ControladorCompra {

    private final ServicioCompra servicioCompra;
    private final ServicioCliente servicioCliente;
    private final EmailSenderService emailSenderService;
    private final RepositorioServicio repositorioServicio;
    private final RepositorioProducto repositorioProducto;

    public ControladorCompra(ServicioCompra servicioCompra, ServicioCliente servicioCliente, EmailSenderService emailSenderService, RepositorioServicio repositorioServicio, RepositorioProducto repositorioProducto) {
        this.servicioCompra = servicioCompra;
        this.servicioCliente = servicioCliente;
        this.emailSenderService = emailSenderService;
        this.repositorioServicio = repositorioServicio;
        this.repositorioProducto = repositorioProducto;
    }
    @PostMapping("/compra")
    @Transactional
    public ResponseEntity<Object> comprar (@RequestBody RealizarCompraDTO realizarCompraDTO,
                                           Authentication authentication) throws MessagingException, IOException {
        Cliente cliente = this.servicioCliente.findByEmail(authentication.getName());
        Compra compra = new Compra();
        //verifica si tiene servicio pero no coloco fecha de turno
        if(!realizarCompraDTO.getServicios().isEmpty()&&realizarCompraDTO.getFechaDelServicio()==null)
            return new ResponseEntity<>("La fecha de servicio no fue ingresada",HttpStatus.BAD_REQUEST);
        //verifica si no tiene productos ni servicios
        if(realizarCompraDTO.getProductos().isEmpty()&&realizarCompraDTO.getServicios().isEmpty())
            return new ResponseEntity<>("Usted no tiene nada en su carrito de compra",HttpStatus.BAD_REQUEST);

        Double montoDeCompra = 0.0;
        for (RealizarCompraServicio servicio : realizarCompraDTO.getServicios()) {
            Servicio servicioObj = this.repositorioServicio.findById(servicio.getId()).orElseThrow();
            montoDeCompra += servicioObj.getPrecio();
        }
        for (RealizarCompraProducto producto : realizarCompraDTO.getProductos()) {
            Producto productoObj = this.repositorioProducto.findById(producto.getId()).orElseThrow();
            montoDeCompra += productoObj.getPrecio() * producto.getCantidad();
        }
        if (montoDeCompra>25000){
            compra.setDescuento(5);
            montoDeCompra -= montoDeCompra * 0.05;
        }

        //verifica que tenga saldo menor al pago y que introdujo numero de tarjeta (revisar)
        if (montoDeCompra>cliente.getCuenta().getSaldo() && realizarCompraDTO.getNumeroTarjetaPago().isEmpty())
            return new ResponseEntity<>("No ingresò el numero de la tarjeta para realizar el pago",HttpStatus.BAD_REQUEST);
        //verifica que tenga saldo menor al pago y que introdujo cvv de tarjeta (revisar)
        if (montoDeCompra>cliente.getCuenta().getSaldo() && realizarCompraDTO.getCvv()==null)
            return new ResponseEntity<>("No ingresò el cvv de la tarjeta para realizar el pago",HttpStatus.BAD_REQUEST);

        if(realizarCompraDTO.isPagarCuentaCliente()) {
            if (montoDeCompra > cliente.getCuenta().getSaldo()){
                montoDeCompra -= cliente.getCuenta().getSaldo();
                cliente.getCuenta().setSaldo(0);
            }

            else if (montoDeCompra < cliente.getCuenta().getSaldo()){
                cliente.getCuenta().setSaldo(cliente.getCuenta().getSaldo() - montoDeCompra);
                montoDeCompra = 0.0;
            }
        }
        if(montoDeCompra > 0){
            //realizar el pago por tarjeta
            String respuesta = servicioCompra.conectarHomebanking(realizarCompraDTO.getNumeroTarjetaPago(), realizarCompraDTO.getCvv(), montoDeCompra, "Pago por compra en Adstore Detailing");
            if (respuesta == "BAD"){
                return new ResponseEntity<>("Pago no realizado, por favor verificar información de tarjeta", HttpStatus.FORBIDDEN);
            }
        }
        compra.setFecha(LocalDateTime.now());
        this.servicioCompra.guardar(compra);
        cliente.getCuenta().sumarCompra(compra);

        if (!realizarCompraDTO.getServicios().isEmpty())
            this.servicioCompra.agregarServicios(realizarCompraDTO.getServicios(),compra,realizarCompraDTO.getFechaDelServicio());
        if (!realizarCompraDTO.getProductos().isEmpty())
            this.servicioCompra.agregarProductos(realizarCompraDTO.getProductos(),compra);

        compra.setMontoFinal(compra.calcularPrecioTotal());

        this.emailSenderService.enviarFactura(
                "Factura de compra #"+compra.getId()+ " -Adstore",
                "Gracias por confiar en nosotros! Adjunta se encuentra su factura.",
                authentication.getName(),
                compra);
        this.servicioCompra.guardar(compra);

        return new ResponseEntity<>(new CompraDTO(compra),HttpStatus.CREATED );

    }}
