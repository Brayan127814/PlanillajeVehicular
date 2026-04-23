package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
<<<<<<< HEAD
    @Column(nullable = false)
=======
    @Column(nullable = false , unique = true)
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "empresaId")
    private  EmpresaEntity empresa;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "puestoId")
=======
    @JoinColumn(name = "puestoId", nullable = true)
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    private  PuestoEntity puesto;

    @ManyToOne
    @JoinColumn(name = "rolId")
    private RolesEntity roles;


}
