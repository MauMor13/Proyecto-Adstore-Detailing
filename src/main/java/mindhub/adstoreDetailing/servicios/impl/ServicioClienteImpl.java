package mindhub.adstoreDetailing.servicios.impl;

import mindhub.adstoreDetailing.dtos.ClienteDTO;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.repositorios.RepositorioCliente;
import mindhub.adstoreDetailing.servicios.ServicioCliente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Service
public class ServicioClienteImpl implements ServicioCliente {
    private final RepositorioCliente repositorioCliente;
    private final PasswordEncoder pwdEncoder;

    public ServicioClienteImpl(RepositorioCliente repositorioCliente, PasswordEncoder pwdEncoder) {
        this.repositorioCliente = repositorioCliente;
        this.pwdEncoder = pwdEncoder;
    }

    @Override
    public ClienteDTO findByIds(Long id) {
        return this.generarCuentaDTO(this.repositorioCliente.findById(id).orElse(null));
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        this.encriptarClave(cliente);
        this.repositorioCliente.save(cliente);
    }

    @Override
    public Cliente findByEmail(String email) {
        return this.repositorioCliente.findByEmail(email);
    }

    @Override
    public boolean emailEsValido(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public List<ClienteDTO> findAllClienteDTO() {
        return repositorioCliente.findAll().stream().map(ClienteDTO::new).collect(toList());
    }

    @Override
    public void guardar(Cliente cliente) {
        this.repositorioCliente.save(cliente);
    }
    @Override
    public Optional<Cliente> findById(Long id){
        return this.repositorioCliente.findById(id);
    }

    private ClienteDTO generarCuentaDTO(Cliente cliente) {
        return new ClienteDTO(cliente);
    }

    private void encriptarClave(Cliente cliente) {
        cliente.setClaveIngreso(pwdEncoder.encode(cliente.getClaveIngreso()));
    }
}
