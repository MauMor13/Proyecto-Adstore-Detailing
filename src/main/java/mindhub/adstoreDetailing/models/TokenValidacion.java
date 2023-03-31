package mindhub.adstoreDetailing.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TokenValidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    @SequenceGenerator(name = "token_seq", sequenceName = "token_id_seq", allocationSize = 1)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String token;

    @OneToOne(targetEntity = Cliente.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "cliente_id")
    private Cliente cliente;

    public TokenValidacion(Cliente cliente) {
        this.cliente = cliente;
        token = UUID.randomUUID().toString();
    }
}