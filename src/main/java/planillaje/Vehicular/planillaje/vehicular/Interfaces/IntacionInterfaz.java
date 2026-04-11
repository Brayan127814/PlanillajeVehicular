package planillaje.Vehicular.planillaje.vehicular.Interfaces;

import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.dtos.InvitacionRequest;
import planillaje.Vehicular.planillaje.vehicular.entidades.InvitacionEntity;

@Component
public interface IntacionInterfaz {
    String crearInvitacion( InvitacionRequest puestoId);
    InvitacionEntity validarInvitacion(String token);
    void marcarComoUsada(InvitacionEntity invitacion);

}
