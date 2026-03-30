package planillaje.Vehicular.planillaje.vehicular.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "puesto")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PuestoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombrePuesto;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name ="empresaId")
    private EmpresaEntity empresa;

    @Column(nullable = false)
    private Integer totalParqueaderos;

    @OneToMany(mappedBy = "puesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParqueaderoEntity> parqueaderos = new ArrayList<>();
}
