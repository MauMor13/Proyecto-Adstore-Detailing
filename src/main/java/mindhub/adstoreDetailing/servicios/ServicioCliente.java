package mindhub.adstoreDetailing.servicios;

import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;

import java.util.Optional;

public interface ServicioCliente {
   ClienteDTO findByIds(Long id);
   Cliente findByEmail(String email);
   void registrarCliente(Cliente cliente);
   boolean emailEsValido(String email);

}