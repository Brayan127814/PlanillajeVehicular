package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.web.bind.annotation.*;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoResponse;
import planillaje.Vehicular.planillaje.vehicular.servicios.ParqueaderoService;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {
    private final ParqueaderoService parqueaderoService;

    public ParqueaderoController(ParqueaderoService parqueaderoService) {
        this.parqueaderoService = parqueaderoService;
    }
<<<<<<< HEAD

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
=======
    @PreAuthorize("hasAuthority('CREAR_PARQUEADEROS')")
    @PostMapping("/registrar")
    public ResponseEntity<Map<String , String>> registrar(@Valid @RequestBody ParqueaderoRequest data) {
        parqueaderoService.registrarParqueadero(data);
        return ResponseEntity.ok(Map.of("mensaje","Parqueaderos creados correctamente"));
    }

    @GetMapping("/ocupadosPaginados")
    public Page<ParqueaderoResponse> parqueaderosLibres(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return parqueaderoService.parqueaderosOcupados(page, size);
    }

    @GetMapping("/parqueaderosPaginados")
    public Page<ParqueaderoResponse> listaPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        return parqueaderoService.paginados(page, size);

    }

    //LIBERAR PARQUEADERO
    @PostMapping("/liberar/{parqueaderoId}")
    public ResponseEntity<String> liberar(@PathVariable Long parqueaderoId) {
        parqueaderoService.liberarParqueadero(parqueaderoId);
        return ResponseEntity.ok("Parqueadero Liberado");
    }
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
