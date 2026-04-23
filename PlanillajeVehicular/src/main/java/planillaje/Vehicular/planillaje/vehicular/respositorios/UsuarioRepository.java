package planillaje.Vehicular.planillaje.vehicular.respositorios;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;

import java.util.Optional;

@Repository
<<<<<<< HEAD
public interface UsuarioRepository extends JpaRepository<UsuarioEntity , Long> {

    Optional<UsuarioEntity> findByUsername(String username);

=======
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUsername(String username);

    //OBTENER LA LISTA DE UNIDADES DEL PUESTO
    Page<UsuarioEntity> findByPuestoId(Long puestoId, Pageable pageable);

    Page<UsuarioEntity> findByPuesto_IdAndEmpresa_Id(Long puestoId, Long empresaId, Pageable pageable);
    boolean existsByUsername(String username);
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
