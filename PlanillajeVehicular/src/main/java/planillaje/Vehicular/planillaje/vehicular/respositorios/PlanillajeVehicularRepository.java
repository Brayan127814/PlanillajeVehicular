package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.PlanillajeVehicularEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;

<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanillajeVehicularRepository extends JpaRepository<PlanillajeVehicularEntity, Long> {

<<<<<<< HEAD
    List<PlanillajeVehicularEntity> findByVehiculoOrderByHoraInicioDesc(VehiculoEntity vehiculo);

    Page<PlanillajeVehicularEntity> findByVehiculoOrderByHoraInicioDesc(VehiculoEntity vehiculo, Pageable pageable);
   /*
    Page<PlanillajeVehicularEntity> findByVehiculo_PlacaAndParqueadero_Puesto_IdOrderByHoraInicioDesc(
            String placa,Long puestoId,Pageable pageable
    );
    */

    @EntityGraph(attributePaths = {
            "vehiculo",
            "parqueadero",
            "parqueadero.puesto",
            "usuario"
    })
    Page<PlanillajeVehicularEntity>
    findByVehiculo_PlacaAndParqueadero_Puesto_IdOrderByHoraInicioDesc(
            String placa,
            Long puestoId,
            Pageable pageable
    );

    @Query("""
            SELECT p FROM PlanillajeVehicularEntity p
            JOIN p.vehiculo v
            JOIN p.parqueadero pa
            WHERE v.placa = :placa
            AND pa.puesto.id = :puestoId
            ORDER BY p.horaInicio DESC
            """)
    Page<PlanillajeVehicularEntity> buscarOptimizado(
            @Param("placa") String placa,
            @Param("puestoId") Long puestoId,
            Pageable pageable
    );
=======
        List<PlanillajeVehicularEntity> findByVehiculoOrderByHoraInicioDesc(VehiculoEntity vehiculo);

        Page<PlanillajeVehicularEntity> findByVehiculoOrderByHoraInicioDesc(VehiculoEntity vehiculo, Pageable pageable);
        /*
         * Page<PlanillajeVehicularEntity>
         * findByVehiculo_PlacaAndParqueadero_Puesto_IdOrderByHoraInicioDesc(
         * String placa,Long puestoId,Pageable pageable
         * );
         */

        @EntityGraph(attributePaths = {
                        "vehiculo",
                        "parqueadero",
                        "parqueadero.puesto",
                        "usuario"
        })
        Page<PlanillajeVehicularEntity> findByVehiculo_PlacaAndParqueadero_Puesto_IdOrderByHoraInicioDesc(
                        String placa,
                        Long puestoId,
                        Pageable pageable);

        @Query("""
                        SELECT p FROM PlanillajeVehicularEntity p
                        JOIN p.vehiculo v
                        JOIN p.parqueadero pa
                        WHERE v.placa = :placa
                        AND pa.puesto.id = :puestoId
                        ORDER BY p.horaInicio DESC
                        """)
        Page<PlanillajeVehicularEntity> buscarOptimizado(
                        @Param("placa") String placa,
                        @Param("puestoId") Long puestoId,
                        Pageable pageable);

        boolean existsByVehiculoAndHoraInicioAfter(VehiculoEntity vehiculo, LocalDateTime horaInicio);

        boolean existsByVehiculoAndHoraInicioGreaterThanEqualAndHoraInicioLessThan(
                        VehiculoEntity vehiculo,
                        LocalDateTime inicioDia,
                        LocalDateTime finDia);

        Page<PlanillajeVehicularEntity> findByPuesto_IdAndHoraInicioGreaterThanEqualAndHoraInicioLessThanOrderByHoraInicioDesc(
                        Long puestoId, LocalDateTime inicioDia, LocalDateTime finDia, Pageable pageable);

        long countByPuesto_IdAndHoraInicioGreaterThanEqualAndHoraInicioLessThan(
                        Long puestoId,
                        LocalDateTime inicioDia,
                        LocalDateTime finDia);
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

}
