package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
<<<<<<< HEAD
=======
import org.springframework.data.jpa.repository.Query;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {

<<<<<<< HEAD

    Optional<VehiculoEntity> findByPlaca(String placa);
    Page<VehiculoEntity> findByParqueadero_Puesto_Id(Long puestoId, Pageable pageable);
    Optional<VehiculoEntity> findByPlacaAndParqueadero_Puesto_Id(String placa, Long puestoId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM VehiculoEntity v WHERE v.placa = :placa")
    Optional<VehiculoEntity> findByIdWithLock(@Param("placa") String placa);
=======
    Optional<VehiculoEntity> findByPlaca(String placa);

    Page<VehiculoEntity> findByParqueadero_Puesto_Id(Long puestoId, Pageable pageable);

    Optional<VehiculoEntity> findByPlacaAndParqueadero_Puesto_Id(String placa, Long puestoId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM VehiculoEntity v WHERE v.placa = :placa")
    Optional<VehiculoEntity> findByPlacaForUpdate(String placa);

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
