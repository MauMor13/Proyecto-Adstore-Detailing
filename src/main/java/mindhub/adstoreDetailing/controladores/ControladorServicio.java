package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.ServicioACrearDTO;
import mindhub.adstoreDetailing.dtos.ServicioDTO;
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
    @GetMapping("/servicios-activos")
    public List<ServicioDTO> traerServiciosActivos(){return servicioServicio.findAllServiciosActivosDTO();}
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
        if(servicio.isActivo()){
            servicioAModificar.get().setActivo(true);
            modificadosSb.append("producto activo, ");
        }

        this.servicioServicio.guardar(servicioAModificar.get());

        modificadosSb.delete(modificadosSb.length()-2,modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }
    @PostMapping("/crear-servicio")
    public ResponseEntity<Object> crearServicio(@RequestBody ServicioACrearDTO servicioACrearDTO) {
        if (servicioACrearDTO.getPrecio() < 0) {
            return new ResponseEntity<>("Ingrese Precio", HttpStatus.BAD_REQUEST);
        }
        if (servicioACrearDTO.getDescripcion() == null) {
            return new ResponseEntity<>("Ingrese Descripción", HttpStatus.BAD_REQUEST);
        }
        if (servicioACrearDTO.getNombre() == null) {
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if (servicioACrearDTO.getImagenURL() == null) {
            return new ResponseEntity<>("Ingrese Imagen", HttpStatus.BAD_REQUEST);
        }
        if (servicioACrearDTO.getDuracion().compareTo(Duration.ofMinutes(5))>0){
            return new ResponseEntity<>("Duración inválida", HttpStatus.BAD_REQUEST);
        }

       Servicio servicioCreado = new Servicio(
               servicioACrearDTO.getNombre(),
               servicioACrearDTO.getDescripcion(),
               servicioACrearDTO.getPrecio(),
               servicioACrearDTO.getDuracion(),
               servicioACrearDTO.getImagenURL());

        this.servicioServicio.guardar(servicioCreado);

        return new ResponseEntity<>(servicioCreado, HttpStatus.CREATED);
    }
    @PatchMapping("/deshabilitar-servicio")
    public ResponseEntity<Object> deshabilitarOHabilitar(@RequestParam Long id){

        Optional<Servicio> servicio = servicioServicio.findById(id);

        if(servicio.isEmpty()){
            return new ResponseEntity<>("Servicio no encontrado", HttpStatus.BAD_REQUEST);
        }

        servicio.get().setActivo(!servicio.get().isActivo());

        servicioServicio.guardar(servicio.get());

        return new ResponseEntity<>(servicio.get().getNombre()+" "+"Servicio activo: " + servicio.get().isActivo(), HttpStatus.OK);
    }
}
