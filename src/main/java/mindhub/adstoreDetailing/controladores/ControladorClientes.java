package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.dtos.RegistroClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.models.TarjetaAd;
import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioTarjetaAd;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import mindhub.adstoreDetailing.utilidades.Utilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ControladorClientes {
    @Autowired
    RepositorioCuenta repositorioCuenta;
    @Autowired
    RepositorioTarjetaAd repositorioTarjetaAd;
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
    public ResponseEntity<Object> registrar(@RequestBody RegistroClienteDTO registroClienteDTO) {

        if (registroClienteDTO.getNombre().isEmpty()) {
            return new ResponseEntity<>("Ingrese Nombre", HttpStatus.BAD_REQUEST);
        }
        if (registroClienteDTO.getApellido().isEmpty()) {
            return new ResponseEntity<>("Ingrese apellido", HttpStatus.BAD_REQUEST);
        }
        if (registroClienteDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("Ingrese Email", HttpStatus.BAD_REQUEST);
        }
        else if (registroClienteDTO.getEmail().endsWith("@admin.adstore") || !servicioCliente.emailEsValido(registroClienteDTO.getEmail())) {
            return new ResponseEntity<>("Email Inválido", HttpStatus.BAD_REQUEST);
        }
        if (registroClienteDTO.getClaveIngreso().isEmpty()) {
            return new ResponseEntity<>("Ingrese Contraseña", HttpStatus.BAD_REQUEST);
        }
        if(registroClienteDTO.getDireccion()==null){
            return new ResponseEntity<>("Ingrese Dirección", HttpStatus.BAD_REQUEST);
        }
        if(registroClienteDTO.getTelefono()==null){
            return new ResponseEntity<>("Ingrese Teléfono", HttpStatus.BAD_REQUEST);
        }
        if (servicioCliente.findByEmail(registroClienteDTO.getEmail()) !=  null) {
            return new ResponseEntity<>("Email ya registrado", HttpStatus.FORBIDDEN);
        }

        Cliente nuevoCliente = new Cliente(
                registroClienteDTO.getNombre(),
                registroClienteDTO.getApellido(),
                registroClienteDTO.getDireccion(),
                registroClienteDTO.getEmail(),
                registroClienteDTO.getClaveIngreso(),
                registroClienteDTO.getTelefono()
                );
        Cuenta nuevaCuenta = new Cuenta(Utilidad.generarNumeroCuenta(repositorioCuenta), 0.0);
        TarjetaAd nuevaTarjeta = new TarjetaAd(Utilidad.generarNumeroTarjetaAd(repositorioTarjetaAd));

        this.servicioCliente.guardar(nuevoCliente);
        this.repositorioCuenta.save(nuevaCuenta);
        this.repositorioTarjetaAd.save(nuevaTarjeta);

        nuevoCliente.setCuenta(nuevaCuenta);
        nuevaCuenta.setCliente(nuevoCliente);

        nuevaCuenta.setTarjetaAd(nuevaTarjeta);
        nuevaTarjeta.setCuenta(nuevaCuenta);

        servicioCliente.registrarCliente(nuevoCliente);
        repositorioCuenta.save(nuevaCuenta);
        repositorioTarjetaAd.save(nuevaTarjeta);
        return new ResponseEntity<>(nuevoCliente,HttpStatus.CREATED);
    }


}
