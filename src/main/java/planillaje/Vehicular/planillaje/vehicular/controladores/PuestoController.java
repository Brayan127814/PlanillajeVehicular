package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoResponse;
import planillaje.Vehicular.planillaje.vehicular.servicios.PuestoService;

@RestController
@RequestMapping("/puestos")
public class PuestoController {
    private final PuestoService puestoService;

    public PuestoController(PuestoService puestoService) {
        this.puestoService = puestoService;
    }

    @PreAuthorize("hasAuthority('CREAR_PUESTO')")
    @PostMapping("/registrar")
    public ResponseEntity<PuestoResponse> registrar(@Valid @RequestBody PuestoRequest data) {
        PuestoResponse puestoResponse = puestoService.registrarPuesto(data);
        return ResponseEntity.ok(puestoResponse);
    }
}
