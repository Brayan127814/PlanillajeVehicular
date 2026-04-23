package planillaje.Vehicular.planillaje.vehicular.respositorios;

<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;

@Repository
public interface PuestoRepository extends JpaRepository<PuestoEntity, Long> {
<<<<<<< HEAD
=======
    //BUSCAR PUESTO POR NOMBRE
    Page<PuestoEntity> findByNombrePuestoContainingIgnoreCase(String nombrePuesto, Pageable pageable);
    //LISTAR TODOS LOS PUESTOS
    Page<PuestoEntity> findByEmpresa_Id(Long empresaId , Pageable pageable);
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
