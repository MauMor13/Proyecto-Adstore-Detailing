package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.repositorios.RepositorioCliente;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioClienteImpl implements ServicioCliente{
    private final RepositorioCliente repositorioCliente;
    private final PasswordEncoder pwdEncoder;
    public ServicioClienteImpl(RepositorioCliente repositorioCliente, PasswordEncoder pwdEncoder) {
        this.repositorioCliente = repositorioCliente;
        this.pwdEncoder = pwdEncoder;
    }

    @Override
    public ClienteDTO findByIds(Long id){
        return this.generarAccountDTO(this.repositorioCliente.findById(id).orElse(null));
    }
    @Override
    public void registrarCliente(Cliente cliente){
        this.repositorioCliente.save(cliente);
    }
    private ClienteDTO generarAccountDTO(Cliente cliente){
        return new ClienteDTO(cliente);
    }
}
