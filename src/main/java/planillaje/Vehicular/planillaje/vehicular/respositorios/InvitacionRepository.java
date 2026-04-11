package planillaje.Vehicular.planillaje.vehicular.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaje.Vehicular.planillaje.vehicular.entidades.InvitacionEntity;

import java.util.Optional;

@Repository
public interface InvitacionRepository extends JpaRepository<InvitacionEntity , Long> {
    Optional<InvitacionEntity> findByToken(String token);
}
