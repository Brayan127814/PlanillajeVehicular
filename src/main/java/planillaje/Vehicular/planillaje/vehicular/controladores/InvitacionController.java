package planillaje.Vehicular.planillaje.vehicular.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import planillaje.Vehicular.planillaje.vehicular.dtos.InvitacionRequest;
import planillaje.Vehicular.planillaje.vehicular.servicios.InvitacionServices;

import java.util.Map;

@RestController
@RequestMapping("/invitaciones")
public class InvitacionController {
    private final InvitacionServices invitacionServices;

    public InvitacionController(InvitacionServices invitacionServices) {
        this.invitacionServices = invitacionServices;
    }

    @PreAuthorize("hasAuthority('CREAR_INVITACION')")
    @PostMapping("/crearInvitacion")
    public ResponseEntity<Map<String, String>> crear(@RequestBody InvitacionRequest puestoId) {
        String token = invitacionServices.crearInvitacion(puestoId);
        return ResponseEntity.ok(Map.of("token", token));

    }
}
