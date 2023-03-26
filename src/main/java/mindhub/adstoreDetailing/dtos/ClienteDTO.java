package mindhub.adstoreDetailing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mindhub.adstoreDetailing.models.Cliente;
import mindhub.adstoreDetailing.models.Cuenta;

import javax.persistence.OneToOne;
@Getter
@NoArgsConstructor
public class ClienteDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private int telefono;
    CuentaDTO cuenta;
    public ClienteDTO(Cliente cliente){
        this.id=cliente.getId();
        this.nombre= cliente.getNombre();
        this.apellido= cliente.getApellido();
        this.direccion= cliente.getDireccion();
        this.email=cliente.getEmail();
        this.telefono= cliente.getTelefono();
    }
}
