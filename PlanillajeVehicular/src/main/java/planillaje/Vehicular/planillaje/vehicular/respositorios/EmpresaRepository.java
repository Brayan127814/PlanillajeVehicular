package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;

<<<<<<< HEAD
@Repository
public interface EmpresaRepository  extends JpaRepository<EmpresaEntity, Long> {
=======
import java.util.Optional;

@Repository
public interface EmpresaRepository  extends JpaRepository<EmpresaEntity, Long> {

     boolean existsByNombreEmpresa(String nombreEmpresa);
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
