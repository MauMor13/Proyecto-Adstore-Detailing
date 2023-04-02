package mindhub.adstoreDetailing.servicios;
import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import java.util.List;
import java.util.Optional;

public interface ServicioCliente {
   ClienteDTO findByIds(Long id);
   Optional<Cliente> findById(Long id);
   Cliente findByEmail(String email);
   void registrarCliente(Cliente cliente);
   void guardar(Cliente cliente);
   boolean emailEsValido(String email);
   List<ClienteDTO> findAllClienteDTO();


}
