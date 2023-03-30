package mindhub.adstoreDetailing.servicios;

import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ServicioProducto {
    List<ProductoDTO> findAllDTOs();
    Optional<Producto> findById(Long id);
    void guardar(Producto producto);
    List<ProductoDTO> productosActivosDTO();
}
