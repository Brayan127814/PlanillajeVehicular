package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUsername(String username);

    //OBTENER LA LISTA DE UNIDADES DEL PUESTO
    Page<UsuarioEntity> findByPuestoId(Long puestoId, Pageable pageable);

    Page<UsuarioEntity> findByPuesto_IdAndEmpresa_Id(Long puestoId, Long empresaId, Pageable pageable);
}
