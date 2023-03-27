package mindhub.adstoreDetailing.controladores;

import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorCliente {
    private final ServicioCliente servicioCliente;
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> register(@RequestBody Cliente cliente) {

        if (cliente.getNombre().isEmpty()) {
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getApellido().isEmpty()) {
            return new ResponseEntity<>("Ingrese apellido", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getEmail().isEmpty()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.BAD_REQUEST);
        }
        else if (cliente.getEmail().endsWith("@admin.adstore") || servicioCliente.emailEsValido(cliente.getEmail())) {
            return new ResponseEntity<>("Invalid Email, you can't use this email", HttpStatus.BAD_REQUEST);
        }
        if (cliente.getClaveIngreso().isEmpty() ) {
            return new ResponseEntity<>("Missing Password", HttpStatus.BAD_REQUEST);
        }
        if (servicioCliente.findByEmail(cliente.getEmail()) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        servicioCliente.registrarCliente(cliente);
        return new ResponseEntity<>("Registrado",HttpStatus.CREATED);
    }


}
