package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String nit;


    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PuestoEntity> puestos = new ArrayList<>();
}
