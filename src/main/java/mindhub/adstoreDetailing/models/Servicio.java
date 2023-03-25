package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_seq")
    @SequenceGenerator(name = "servicio_seq", sequenceName = "servicio_id_seq", allocationSize = 1)
    private long id;
    private String nombreServicio;
    private String descripcion;
    private double precio;
    private int descuento;
    private Duration duracion;
    private String imagenURL;

    @OneToMany(mappedBy = "servicios", fetch = FetchType.EAGER)
    Set<CompraServicio> compraServicios;

}
