package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.PedidoCompraDTO;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraDTO;
import mindhub.adstoreDetailing.dtos.realizarCompra.RealizarCompraServicio;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControladorCompra {
    private final ServicioCompra servicioCompra;
    private final ServicioCliente servicioCliente;

    public ControladorCompra(ServicioCompra servicioCompra, ServicioCliente servicioCliente) {
        this.servicioCompra = servicioCompra;
        this.servicioCliente = servicioCliente;
    }

    @PostMapping("/nueva-compra")
    public void nuevaCompra(Authentication authentication,
                            @RequestBody(required = false) PedidoCompraDTO pedidoCompraDTO){

        for(int i=0;i<pedidoCompraDTO.getProductos().size();i++){

        }
    }

        @PostMapping("/compra")
        public ResponseEntity<Object> comprar (@RequestBody RealizarCompraDTO realizarCompraDTO, Authentication authentication){

            Cliente cliente = this.servicioCliente.findByEmail(authentication.getName());

            Compra compra = new Compra();
            compra.setFecha(LocalDateTime.now());

            this.servicioCompra.agregarServicios(realizarCompraDTO.getServicios(),compra);

            this.servicioCompra.agregarProductos(realizarCompraDTO.getProductos(),compra);

            compra.setMontoFinal(compra.calcularPrecioTotal());

            return new ResponseEntity<>(compra,HttpStatus.CREATED );

        }

}
