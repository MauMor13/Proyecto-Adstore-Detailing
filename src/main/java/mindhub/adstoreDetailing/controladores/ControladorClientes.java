package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ControladorClientes {
    private final ServicioCliente servicioCliente;
    public ControladorClientes(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @GetMapping("/cliente")
    public ClienteDTO traerCliente(Authentication auth){
        return new ClienteDTO(this.servicioCliente.findByEmail(auth.getName()));
    }
    @GetMapping("/clientes")
    public List<ClienteDTO> traerClientes(){
        return servicioCliente.findAllCliente().stream().map(ClienteDTO::new).collect(toList());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@RequestBody Cliente cliente) {

        if (cliente.getNombre().isEmpty()) {
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getApellido().isEmpty()) {
            return new ResponseEntity<>("Ingrese apellido", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getEmail().isEmpty()) {
            return new ResponseEntity<>("Ingrese Email", HttpStatus.BAD_REQUEST);
        }
        else if (cliente.getEmail().endsWith("@admin.adstore") || servicioCliente.emailEsValido(cliente.getEmail())) {
            return new ResponseEntity<>("Invalid Email, you can't use this email", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getClaveIngreso().isEmpty() ) {
            return new ResponseEntity<>("Ingrese Password", HttpStatus.BAD_REQUEST);
        }
        if (servicioCliente.findByEmail(cliente.getEmail()) !=  null) {
            return new ResponseEntity<>("Email ya registrado", HttpStatus.FORBIDDEN);
        }

        servicioCliente.registrarCliente(cliente);
        return new ResponseEntity<>("Registrado",HttpStatus.CREATED);
    }


}
