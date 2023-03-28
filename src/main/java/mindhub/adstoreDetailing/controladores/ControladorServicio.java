package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.dtos.ServicioDTO;
import mindhub.adstoreDetailing.models.Producto;
import mindhub.adstoreDetailing.models.Servicio;
import mindhub.adstoreDetailing.servicios.ServicioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControladorServicio {
    private final ServicioServicio servicioServicio;

    public ControladorServicio(ServicioServicio servicioServicio) {
        this.servicioServicio = servicioServicio;
    }

    @GetMapping("/servicios")
    public List<ServicioDTO> traerServicios() {
        return this.servicioServicio.findAllServicioDTO();
    }
    @PatchMapping("/modificar-servicio")
    public ResponseEntity<Object> modificarServicio(@RequestBody ServicioDTO servicio){

        Optional<Servicio> servicioAModificar = this.servicioServicio.findById(servicio.getId());

        StringBuilder modificadosSb = new StringBuilder().append("Se modificó: ");

        if (servicioAModificar.isEmpty()){
            return new ResponseEntity<>("Servicio no encontrado", HttpStatus.BAD_REQUEST);
        }
        if(servicio.getPrecio()>0 ){
            servicioAModificar.get().setPrecio(servicio.getPrecio());
            modificadosSb.append("precio, ");
        }
        if(servicio.getDescripcion()!=null){
            servicioAModificar.get().setDescripcion(servicio.getDescripcion());
            modificadosSb.append("descripción, ");
        }
        if(servicio.getNombre()!=null){
            servicioAModificar.get().setNombre(servicio.getNombre());
            modificadosSb.append("nombre, ");
        }
        if(servicio.getImagenURL()!=null){
            servicioAModificar.get().setImagenURL(servicio.getImagenURL());
            modificadosSb.append("imagen, ");
        }
        if (servicio.getDuracion().compareTo(Duration.ofMinutes(5))>0){
            servicioAModificar.get().setDuracion(servicio.getDuracion());
            modificadosSb.append("duración, ");
        }
        modificadosSb.delete(modificadosSb.length()-2,modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }
}
