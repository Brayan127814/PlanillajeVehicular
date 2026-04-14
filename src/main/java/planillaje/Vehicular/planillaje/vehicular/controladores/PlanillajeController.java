package planillaje.Vehicular.planillaje.vehicular.controladores;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeResponse;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.servicios.PlanillajeService;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("/planillajeVehicular")
public class PlanillajeController {
    private final PlanillajeService planillajeService;

    public PlanillajeController(PlanillajeService planillajeService) {
        this.planillajeService = planillajeService;
    }

    @PreAuthorize("hasAuthority('CREAR_PLANILLAJE')")
    @PostMapping("/registrar")
    public ResponseEntity<PlanillajeResponse> registrar(@RequestBody PlanillajeRequest data) {
        PlanillajeResponse planillaje = planillajeService.registrarPlanillaje(data);
        return ResponseEntity.ok(planillaje);
    }

    /*
    LISTAR EL HISTORIAL DEL PLANILLAJE DE UN VEHICULO

     */
    @PreAuthorize("hasAuthority('LISTAR_PLANILLAJE')")
    @GetMapping("/placa")
    public ResponseEntity<List<PlanillajeResponse>> listarHistorial(@RequestParam String placa) {
        return ResponseEntity.ok(planillajeService.listarPlanillajePorPlaca(placa));
    }

    @PreAuthorize("hasAuthority('LISTAR_PLANILLAJE')")
    @GetMapping("/paginados")
    public Page<PlanillajeResponse> vehiculoPlanillado(@RequestParam String placa, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return planillajeService.planillajePaginadosPlaca(placa, page, size);
    }
}
