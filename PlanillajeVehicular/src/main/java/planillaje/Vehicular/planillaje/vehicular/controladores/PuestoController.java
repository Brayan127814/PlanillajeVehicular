package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
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

<<<<<<< HEAD
    @PostMapping("/registrar")
    public ResponseEntity<PuestoResponse> registrar(@Valid @RequestBody PuestoRequest data){
         PuestoResponse puestoResponse = puestoService.registrarPuesto(data);
         return  ResponseEntity.ok(puestoResponse);
=======
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
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    }
}
