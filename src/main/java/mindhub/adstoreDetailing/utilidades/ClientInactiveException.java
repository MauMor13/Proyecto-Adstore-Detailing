package mindhub.adstoreDetailing.utilidades;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ClientInactiveException extends AuthenticationException {
    public ClientInactiveException(String message) {
        super(message);
    }
}