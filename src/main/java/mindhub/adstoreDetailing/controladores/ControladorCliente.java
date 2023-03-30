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

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ControladorCliente {
    @Autowired
    RepositorioCuenta repositorioCuenta;
    @Autowired
    RepositorioTarjetaAd repositorioTarjetaAd;
    private final ServicioCliente servicioCliente;
    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @GetMapping("/cliente")
    public ClienteDTO traerCliente(Authentication auth){
        return new ClienteDTO(this.servicioCliente.findByEmail(auth.getName()));
    }
    @GetMapping("/clientes")
    public List<ClienteDTO> traerClientes(){
        return servicioCliente.findAllClienteDTO();
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
        return new ResponseEntity<>("Se registró con éxito",HttpStatus.CREATED);
    }
    @PatchMapping("/modificar-cliente")
    public ResponseEntity<Object> modificarCliente(@RequestBody ClienteDTO clienteDTO){

        if(clienteDTO.getId()<0){
            return new ResponseEntity<>("Id inválido", HttpStatus.FORBIDDEN);
        }
        Optional<Cliente> cliente = this.servicioCliente.findById(clienteDTO.getId());

        if(cliente.isEmpty()){
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.FORBIDDEN);
        }

        StringBuilder modificadosSb = new StringBuilder().append("Se modificó: ");

        if(clienteDTO.getNombre()!=null){
            cliente.get().setNombre(clienteDTO.getNombre());
            modificadosSb.append("nombre, ");
        }
        if(clienteDTO.getApellido()!=null){
            cliente.get().setApellido(clienteDTO.getApellido());
            modificadosSb.append("apellido, ");
        }
        if(clienteDTO.getEmail()!=null){
            cliente.get().setEmail(clienteDTO.getEmail());
            modificadosSb.append("email, ");
        }
        if(clienteDTO.getDireccion()!=null){
            cliente.get().setDireccion(clienteDTO.getDireccion());
            modificadosSb.append("dirección, ");
        }
        if(clienteDTO.getTelefono()!=null){
            cliente.get().setTelefono(clienteDTO.getTelefono());
            modificadosSb.append("teléfono, ");
        }

        this.servicioCliente.guardar(cliente.get());

        modificadosSb.delete(modificadosSb.length()-2,modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }

}
