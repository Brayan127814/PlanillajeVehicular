package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Invitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String token;

    @Column(nullable = false)
    private Long empresaId;

    @Column(nullable = false)
    private Long puestoId;

    @Column(nullable = false)
    private boolean usada = false;

}
