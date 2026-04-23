package planillaje.Vehicular.planillaje.vehicular.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planillaje.Vehicular.planillaje.vehicular.enums.NovedadesPlanillaje;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planillaje")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanillajeVehicularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //HORA DE INICIO DEL RECORRIDO
    @Column(nullable = false)
    private LocalDateTime horaInicio;


    @ManyToOne
    @JoinColumn(name = "puestoId", nullable = false)
    private PuestoEntity puesto;
    //Usuario quien hace el recorrido
    // el planillaje solo es hecho por un usuario

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private UsuarioEntity usuario;

    //Vehiculo al que se le hace el planillaje
    @ManyToOne
    @JoinColumn(name = "vehiculoId", nullable = false)
    private VehiculoEntity vehiculo;

    // Parquedero
    @ManyToOne
    @JoinColumn(name = "parqueaderoID", nullable = false)
    private ParqueaderoEntity parqueadero;

    @Enumerated(EnumType.STRING)
    private NovedadesPlanillaje novedades;


    @Column(length = 255)
    private String detalle;

    @OneToMany(mappedBy = "planillajeVehicularEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotosPlanillajeEntity> fotos = new ArrayList<>();

}
