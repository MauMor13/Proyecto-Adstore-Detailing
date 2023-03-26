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

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Cliente cliente) {

        if (incomingObj.getFirstName().isEmpty() ) {
            return new ResponseEntity<>("Missing First Name", HttpStatus.BAD_REQUEST);
        }
        if (incomingObj.getLastName().isEmpty() ) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.BAD_REQUEST);
        }
        if (incomingObj.getEmail().isEmpty()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.BAD_REQUEST);
        }
        else if (incomingObj.getEmail().endsWith("@admin.mindhub")) {
            return new ResponseEntity<>("Invalid Email, you can't use this email", HttpStatus.BAD_REQUEST);
        }
        if (incomingObj.getPassword().isEmpty() ) {
            return new ResponseEntity<>("Missing Password", HttpStatus.BAD_REQUEST);
        }

        if (clientService.findByEmail(incomingObj.getEmail()) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        clientService.saveNewClient(cliente);
        return new ResponseEntity<>("Logged in",HttpStatus.CREATED);
    }


}
