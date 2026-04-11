package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasAuthority('LISTAR_PUESTO')")
    @GetMapping("/allPuestos")
    public Page<PuestoResponse> listarPuestos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return puestoService.listarPuestos(page, size);
    }
}
