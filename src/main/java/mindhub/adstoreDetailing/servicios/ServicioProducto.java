package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.dtos.ProductoDTO;
import mindhub.adstoreDetailing.models.Producto;
import java.util.List;
import java.util.Optional;

public interface ServicioProducto {
    List<ProductoDTO> findAllDTOs();
    Optional<Producto> findById(Long id);
    void guardar(Producto producto);
    List<ProductoDTO> productosActivosDTO();
    List<Producto> findByActiveTrue();
    public List<ProductoDTO> findByActiveTrueDTO();
}
