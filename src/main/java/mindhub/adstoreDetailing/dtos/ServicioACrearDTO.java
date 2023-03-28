package mindhub.adstoreDetailing.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.Duration;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioACrearDTO {
    private String nombre;
    private String descripcion;
    private double precio;
    private Duration duracion;
    private String imagenURL;
}
