package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {


    Optional<VehiculoEntity> findByPlaca(String placa);
    Page<VehiculoEntity> findByParqueadero_Puesto_Id(Long puestoId, Pageable pageable);
    Optional<VehiculoEntity> findByPlacaAndParqueadero_Puesto_Id(String placa, Long puestoId);


}
