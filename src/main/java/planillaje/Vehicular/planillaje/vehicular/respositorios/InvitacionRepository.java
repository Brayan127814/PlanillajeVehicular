package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import planillaje.Vehicular.planillaje.vehicular.entidades.InvitacionEntity;

import java.util.Optional;

@Repository
public interface InvitacionRepository extends JpaRepository<InvitacionEntity, Long> {
   

    // Para evitar condiciones de carrera, bloqueamos la fila al buscar por token
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InvitacionEntity i WHERE i.token = :token")
    Optional<InvitacionEntity> findByTokenWithLock(@Param("token") String token);
}
