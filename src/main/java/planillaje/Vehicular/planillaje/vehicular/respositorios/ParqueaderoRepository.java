package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.ParqueaderoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParqueaderoRepository extends JpaRepository<ParqueaderoEntity, Long> {
    boolean existsByNumeroParqueaderoAndPuestoId(Integer numeroParqueadero, Long puestoId);
    boolean existsByPuestoId(Long puestoId);

    List<ParqueaderoEntity> findByPuestoAndEstadoOrderByNumeroParqueaderoAsc(PuestoEntity  puesto , ParqueaderoEstado estado);
    Page<ParqueaderoEntity> findByPuestoAndEstado(PuestoEntity puesto, ParqueaderoEstado estado , Pageable pageable);
}
