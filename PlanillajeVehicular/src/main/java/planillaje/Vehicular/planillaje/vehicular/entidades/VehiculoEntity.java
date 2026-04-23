package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VehiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parqueaderoId", unique = true)
    private  ParqueaderoEntity parqueadero;
}
