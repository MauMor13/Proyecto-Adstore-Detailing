package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
    @Setter
    @NoArgsConstructor
    @Entity
    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
        @SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
        private long id;
        private String nombre;
        private String apellido;
        private String direccion;
        private String email;
        private String claveIngreso;
        private int telefono;
    @OneToOne(mappedBy = "cliente",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cuenta cuenta;
    public Cliente(String nombre, String apellido, String direccion, String email, String claveIngreso, int telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.claveIngreso = claveIngreso;
        this.telefono = telefono;
    }
}

