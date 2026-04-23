package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.FotosPlanillajeEntity;

@Repository
public interface FotosRepository extends JpaRepository<FotosPlanillajeEntity, Long> {
}
