package mindhub.adstoreDetailing.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Categoria;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoACrearDTO {
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenUrl;
    private Categoria categoria;
}
