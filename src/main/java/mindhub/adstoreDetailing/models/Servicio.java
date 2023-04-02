package mindhub.adstoreDetailing.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_seq")
    @SequenceGenerator(name = "servicio_seq", sequenceName = "servicio_id_seq", allocationSize = 1)
    private long id;
    private String nombre;
    @Lob
    private String descripcion;
    private double precio;
    private Duration duracion;
    private String imagenURL;
    private boolean activo;
    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER)
    private Set<CompraServicio> compraServicio = new HashSet<>();

    public Servicio(String nombre, String descripcion, double precio, Duration duracion, String imagenURL) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracion = duracion;
        this.imagenURL = imagenURL;
        this.activo = true;
    }
}
