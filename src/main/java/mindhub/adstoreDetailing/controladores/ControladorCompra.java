package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.PedidoCompraDTO;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorCompra {
    private ServicioCompra servicioCompra;

    @PostMapping("/nueva-compra")
    public void nuevaCompra(Authentication authentication,
                            @RequestBody(required = false) PedidoCompraDTO pedidoCompraDTO){




        Compra nuevaCompra = new Compra();
        for(int i=0;i<pedidoCompraDTO.getProductos().size();i++){
            pedidoCompraDTO.getProductos().get(i).;
        }
    }
}
[
        [4,4][3,1][1,2]
        ]

        [
        {id:1,cantidad:2},{id:2,cantidad:3}
                ]