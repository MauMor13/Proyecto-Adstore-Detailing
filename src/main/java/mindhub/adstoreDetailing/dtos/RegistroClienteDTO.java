package mindhub.adstoreDetailing.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Cuenta;
import mindhub.adstoreDetailing.repositorios.RepositorioCuenta;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClienteDTO {
    @Autowired
    RepositorioCuenta repositorioCuenta;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String claveIngreso;
    private String telefono;
    private Cuenta cuenta;
}
