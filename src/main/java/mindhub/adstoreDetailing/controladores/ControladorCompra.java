package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.CompraDTO;

import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Compra;
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
    public ControladorCompra(ServicioCompra servicioCompra, ServicioCliente servicioCliente, EmailSenderService emailSenderService) {
        this.servicioCompra = servicioCompra;
        this.servicioCliente = servicioCliente;
        this.emailSenderService = emailSenderService;
    }

        @PostMapping("/compra")
        @Transactional
        public ResponseEntity<Object> comprar (@RequestBody RealizarCompraDTO realizarCompraDTO, Authentication authentication) throws MessagingException, IOException {

            Cliente cliente = this.servicioCliente.findByEmail(authentication.getName());

            Compra compra = new Compra();
            compra.setFecha(LocalDateTime.now());
            this.servicioCompra.guardar(compra);

            cliente.getCuenta().sumarCompra(compra);


            this.servicioCompra.agregarServicios(realizarCompraDTO.getServicios(),compra);

            this.servicioCompra.agregarProductos(realizarCompraDTO.getProductos(),compra);

            compra.setMontoFinal(compra.calcularPrecioTotal());

            this.servicioCompra.guardar(compra);

            this.emailSenderService.enviarEmail(
                    "Factura de compra "+compra.getId(),
                    "Gracias por su compra! -Adstore Detailing",
                    authentication.getName());

            return new ResponseEntity<>(new CompraDTO(compra),HttpStatus.CREATED );

        }
}