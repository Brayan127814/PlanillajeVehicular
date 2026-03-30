package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;

@Repository
public interface PuestoRepository extends JpaRepository<PuestoEntity, Long> {
}
