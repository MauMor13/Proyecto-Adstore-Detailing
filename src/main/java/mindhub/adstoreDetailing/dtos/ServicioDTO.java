package mindhub.adstoreDetailing.dtos;
import mindhub.adstoreDetailing.models.Servicio;
import javax.persistence.Lob;
import java.time.Duration;

public class ServicioDTO {
    private long id;
    private String nombre;
    @Lob
    private String descripcion;
    private double precio;
    private Duration duracion;
    private String imagenURL;

    public ServicioDTO(Servicio servicio) {
        this.id = servicio.getId();
        this.nombre = servicio.getNombre();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecio();
        this.duracion = servicio.getDuracion();
        this.imagenURL = servicio.getImagenURL();
    }
}
