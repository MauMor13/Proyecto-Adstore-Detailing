package mindhub.adstoreDetailing.dtos.realizarCompra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
public class RealizarCompraDTO {
    private ArrayList<RealizarCompraProducto> productos;
    private ArrayList<RealizarCompraServicio> servicios;
    private LocalDateTime fechaDelServicio;
    private String numeroTarjetaPago;
    private String cvv;
    private boolean pagarCuentaCliente = false;

    public RealizarCompraDTO(ArrayList<RealizarCompraProducto> productos, ArrayList<RealizarCompraServicio> servicios,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDelServicio, String numeroTarjetaPago, String cvv, boolean pagarCuentaCliente) {
        this.productos = productos;
        this.servicios = servicios;
        this.fechaDelServicio =  fechaDelServicio;
        this.numeroTarjetaPago = numeroTarjetaPago;
        this.cvv = cvv;
        this.pagarCuentaCliente = pagarCuentaCliente;
    }
}
