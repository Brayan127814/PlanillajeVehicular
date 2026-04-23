package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;

@Entity
@Table(name = "parqueadero")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParqueaderoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numeroParqueadero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParqueaderoEstado estado;

    /*
     MUCHOS PARQUEADEROS PERTENECEN A UN SOLO PUESO
     Y UN PUESTO TIENE MUCHOS PARQUEADERO
     */
    @ManyToOne
    @JoinColumn(name = "puestoId")
    private PuestoEntity puesto;

    @OneToOne(mappedBy = "parqueadero")
    private VehiculoEntity vehiculo;
}
