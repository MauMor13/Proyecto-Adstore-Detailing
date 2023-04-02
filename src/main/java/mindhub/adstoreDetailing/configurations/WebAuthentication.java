package mindhub.adstoreDetailing.configurations;

import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.repositorios.RepositorioCliente;
import mindhub.adstoreDetailing.utilidades.ClientInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email ->{
            Cliente cliente = repositorioCliente.findByEmail(email);
            if (cliente.getEmail().equalsIgnoreCase("admin@storage.com")){
                return new User(cliente.getEmail(), passwordEncoder().encode(cliente.getClaveIngreso()) , AuthorityUtils.createAuthorityList("ADMIN"));
            } else if (cliente != null && cliente.isActivo()){
                return new User(cliente.getEmail(), cliente.getClaveIngreso(), AuthorityUtils.createAuthorityList("CLIENTE"));
            }
            else if (cliente != null && !cliente.isActivo()){
                throw new ClientInactiveException("Cuenta fue eliminada, desea recuperar su cuenta?");
            }
            else{
                throw new UsernameNotFoundException("Usuario desconocido o no registrado" +  email);
            }
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder(){return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
