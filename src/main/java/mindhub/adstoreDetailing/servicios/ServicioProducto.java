package mindhub.adstoreDetailing.servicios;

import mindhub.adstoreDetailing.dtos.ProductoDTO;

import java.util.List;

public interface ServicioProducto {
    List<ProductoDTO> findAllDTOs();
}
