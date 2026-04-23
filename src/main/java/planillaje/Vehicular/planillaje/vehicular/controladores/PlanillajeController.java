package planillaje.Vehicular.planillaje.vehicular.controladores;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeResponse;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.servicios.PlanillajeService;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @PreAuthorize("hasAuthority('LISTAR_PLANILLAJE')")
    @GetMapping("/paginados")
    public Page<PlanillajeResponse> vehiculoPlanillado(@RequestParam String placa,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return planillajeService.planillajePaginadosPlaca(placa, page, size);
    }

    @PreAuthorize("hasAuthority('LISTAR_PLANILLAJE')")
    @GetMapping("/todos")
    public Page<PlanillajeResponse> vehiculoPlanilladoTodos(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        return planillajeService.listarPlanillajePorDia(page, size);
    }

    @PreAuthorize("hasAuthority('LISTAR_PLANILLAJE')")
    @GetMapping("/totalPordia")
    public ResponseEntity<Long> totalPlanillajePorDia(@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Long total = planillajeService.contarPlanillajePorDia(fecha);
        return ResponseEntity.ok(total);
    }
}
