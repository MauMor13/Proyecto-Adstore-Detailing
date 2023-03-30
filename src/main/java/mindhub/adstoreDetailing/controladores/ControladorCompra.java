package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.PedidoCompraDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Compra;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.models.Servicio;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import mindhub.adstoreDetailing.servicios.ServicioCompra;
import mindhub.adstoreDetailing.servicios.ServicioProducto;
import mindhub.adstoreDetailing.servicios.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ControladorCompra {
    @Autowired
    private ServicioCompra servicioCompra;
    @Autowired
    private ServicioServicio servicioServicio;
    @Autowired
    private ServicioProducto servicioProducto;
    @Autowired
    private ServicioCliente servicioCliente;

    @PostMapping("/nueva-compra")
    public void nuevaCompra(Authentication authentication,
                            @RequestBody(required = false) PedidoCompraDTO pedidoCompraDTO){
        Cliente cliente = servicioCliente.findByEmail(authentication.getName());
        Compra nuevaCompra = new Compra();
        nuevaCompra.setCuenta(cliente.getCuenta());
        servicioCompra.guardar(nuevaCompra);
        Double montoFinal = 0.0;
        int descuento = 0;
        LocalDateTime turno = pedidoCompraDTO.getFechaServicio();
        List<Producto> productos = pedidoCompraDTO.getProductos()
                .stream()
                .map(producto->servicioProducto.findById(producto.getId()).orElse(null))
                .collect(Collectors.toList());
        List<Servicio> servicios = pedidoCompraDTO.getServicios()
                .stream()
                .map(producto->servicioServicio.findById(producto.getId()).orElse(null))
                .collect(Collectors.toList());
        for (int i =0;i<pedidoCompraDTO.getProductos().size();i++){
            Producto producto = servicioProducto.findById(pedidoCompraDTO.getProductos().get(i).getId()).orElse(null);
            montoFinal += producto.getPrecio() * pedidoCompraDTO.getProductos().get(i).getCantidad();
        }
        for (int i = 0;i<pedidoCompraDTO.getServicios().size();i++){
            Servicio servicio = servicioServicio.findById(pedidoCompraDTO.getServicios().get(i).getId()).orElse(null);
            montoFinal += servicio.getPrecio();
        }



    }
}