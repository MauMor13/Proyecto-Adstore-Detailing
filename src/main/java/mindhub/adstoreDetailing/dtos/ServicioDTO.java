package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Servicio;

import javax.persistence.Lob;
import java.time.Duration;

@Getter
@NoArgsConstructor
public class ServicioDTO {
    private long id;
    private String nombre;
    @Lob
    private String descripcion;
    private double precio;
    private Duration duracion;
    private String imagenURL;
    private boolean activo;

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.nombre = servicio.getNombre();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
        this.duracion = servicio.getDuracion();
        this.imagenURL = servicio.getImagenURL();
        this.activo = servicio.isActivo();
    }
}
