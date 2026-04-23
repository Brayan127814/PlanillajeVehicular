package planillaje.Vehicular.planillaje.vehicular.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @OneToMany(mappedBy = "roles")
    private Set<UsuarioEntity> usuario = new HashSet<>();

<<<<<<< HEAD
=======
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "rolId"),
            inverseJoinColumns = @JoinColumn(name = "permisoId")
    )
    private Set<PermisosEntity> permisos = new HashSet<>();

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

}
