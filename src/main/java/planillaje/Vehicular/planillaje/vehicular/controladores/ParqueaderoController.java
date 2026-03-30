package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoResponse;
import planillaje.Vehicular.planillaje.vehicular.servicios.ParqueaderoService;

import java.util.List;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {
    private final ParqueaderoService parqueaderoService;

    public ParqueaderoController(ParqueaderoService parqueaderoService) {
        this.parqueaderoService = parqueaderoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody ParqueaderoRequest data) {
        parqueaderoService.registrarParqueadero(data);
        return ResponseEntity.ok("Parqueaderos creados correctamente");
    }

    @GetMapping("/libres")
    public  ResponseEntity<List<ParqueaderoResponse>> parqueaderosLibres(){
         List<ParqueaderoResponse> parqueaderoResponse = parqueaderoService.parqueaderosDisponibles();
         return  ResponseEntity.ok(parqueaderoResponse);
    }

    @GetMapping("/parqueaderosPaginados")
    public Page<ParqueaderoResponse> listaPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size){
         return parqueaderoService.paginados(page, size);

    }
}
