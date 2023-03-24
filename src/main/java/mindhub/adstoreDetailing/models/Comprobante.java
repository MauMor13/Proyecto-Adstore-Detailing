package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comprobante_seq")
    @SequenceGenerator(name = "comprobante_seq", sequenceName = "comprobante_id_seq", allocationSize = 1)
    private long id;
    private LocalDateTime fecha;
}
