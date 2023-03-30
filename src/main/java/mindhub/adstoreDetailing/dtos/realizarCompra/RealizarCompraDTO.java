package mindhub.adstoreDetailing.dtos.realizarCompra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealizarCompraDTO {
    private LocalDateTime fecha;
    private ArrayList<RealizarCompraProducto> productos;
    private ArrayList<RealizarCompraServicio> servicios;
}
