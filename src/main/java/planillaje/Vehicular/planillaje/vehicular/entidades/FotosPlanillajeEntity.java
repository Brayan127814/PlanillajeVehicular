package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fotos")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotosPlanillajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String fotoBase64;

    @ManyToOne
    @JoinColumn(name = "planillajeId")
    private PlanillajeVehicularEntity planillajeVehicularEntity;
}
