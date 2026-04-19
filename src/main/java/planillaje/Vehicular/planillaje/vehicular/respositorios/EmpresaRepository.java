package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;

import java.util.Optional;

@Repository
public interface EmpresaRepository  extends JpaRepository<EmpresaEntity, Long> {

     boolean existsByNombreEmpresa(String nombreEmpresa);
}
