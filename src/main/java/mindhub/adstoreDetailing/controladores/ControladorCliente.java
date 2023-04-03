package mindhub.adstoreDetailing.controladores;
import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.dtos.RegistroClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.models.TarjetaAd;
import mindhub.adstoreDetailing.models.TokenValidacion;
import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioTarjetaAd;
import mindhub.adstoreDetailing.repositorios.RepositorioToken;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import mindhub.adstoreDetailing.servicios.envioEmail.EmailSenderService;
import mindhub.adstoreDetailing.utilidades.Utilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControladorCliente {
    @Autowired
    RepositorioCuenta repositorioCuenta;
    @Autowired
    RepositorioTarjetaAd repositorioTarjetaAd;
    @Autowired
    RepositorioToken repositorioToken;
    private final ServicioCliente servicioCliente;
    private final EmailSenderService emailSenderService;
    public ControladorCliente(ServicioCliente servicioCliente, EmailSenderService emailSenderService) {
        this.servicioCliente = servicioCliente;
        this.emailSenderService = emailSenderService;
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
    public ResponseEntity<Object> registrar(@RequestBody RegistroClienteDTO registroClienteDTO) throws MessagingException, IOException {

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

        TokenValidacion token = new TokenValidacion(nuevoCliente);
        repositorioToken.save(token);

        nuevoCliente.setCuenta(nuevaCuenta);
        nuevaCuenta.setCliente(nuevoCliente);

        nuevaCuenta.setTarjetaAd(nuevaTarjeta);
        nuevaTarjeta.setCuenta(nuevaCuenta);

        servicioCliente.registrarCliente(nuevoCliente);
        repositorioCuenta.save(nuevaCuenta);
        repositorioTarjetaAd.save(nuevaTarjeta);

        this.emailSenderService.enviarCodigo(nuevoCliente.getEmail(), token);
        return new ResponseEntity<>("Por favor revise su bandeja de entrada",HttpStatus.CREATED);
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
        if(!clienteDTO.isActivo()){
            cliente.get().setActivo(false);
            modificadosSb.append("cliente inactivo, ");
        }
        if(clienteDTO.isActivo()){
            cliente.get().setActivo(true);
            modificadosSb.append("cliente activo, ");
        }

        this.servicioCliente.guardar(cliente.get());

        modificadosSb.delete(modificadosSb.length()-2,modificadosSb.length());
        modificadosSb.append(".");
        String modificaciones = modificadosSb.toString();
        return new ResponseEntity<>(modificaciones, HttpStatus.OK);
    }

    @PatchMapping("/eliminar-cliente")
    public ResponseEntity<Object> eliminarCliente (Authentication authentication){
        Cliente cliente = this.servicioCliente.findByEmail(authentication.getName());

        if(cliente==null){
            return new ResponseEntity<>("Cliente no encontrado",HttpStatus.BAD_REQUEST);
        }

        cliente.setActivo(false);
        this.servicioCliente.guardar(cliente);

        return new ResponseEntity<>("Cliente eliminado", HttpStatus.OK);
    }
    @PatchMapping("/reactivar-cliente")
    public ResponseEntity<Object> reactivarCliente (Authentication authentication){
        Cliente cliente = this.servicioCliente.findByEmail(authentication.getName());

        if(cliente==null){
            return new ResponseEntity<>("Cliente no encontrado",HttpStatus.BAD_REQUEST);
        }

        cliente.setActivo(true);
        this.servicioCliente.guardar(cliente);

        return new ResponseEntity<>("Cliente reactivado", HttpStatus.OK);
    }
    @GetMapping("/confirmar-registro")
    public void confirmarRegistro(@RequestParam String token, HttpServletResponse response) throws ValidationException {
       Optional<TokenValidacion> tokenValidacion = repositorioToken.findByToken(token);

       if(tokenValidacion.isPresent()){
          Cliente cliente = this.servicioCliente.findByEmail(tokenValidacion.get().getCliente().getEmail());
          cliente.setActivo(true);
          this.servicioCliente.guardar(cliente);
           response.setHeader("Location", "/web/email-verificado.html");
           response.setStatus(HttpServletResponse.SC_FOUND);
       }
       else{
          throw new ValidationException("Token inválido");
       }
    }

}
