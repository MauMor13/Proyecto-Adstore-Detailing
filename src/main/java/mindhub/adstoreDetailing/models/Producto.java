package mindhub.adstoreDetailing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_id_seq", allocationSize = 1)
    private long id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenUrl;
    private Categoria categoria;
    @OneToMany(mappedBy = "producto",fetch = FetchType.EAGER)
    private Set<CompraProducto> compraProducto = new HashSet<>();
}
