package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import planillaje.Vehicular.planillaje.vehicular.dtos.VehiculoRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.VehiculoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;
import planillaje.Vehicular.planillaje.vehicular.servicios.VehiculoService;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<VehiculoResponse> registrar(@Valid @RequestBody VehiculoRequest data) {
        VehiculoResponse vehiculoResponse = vehiculoService.registrarVehiculo(data);
        return ResponseEntity.ok(vehiculoResponse);
    }

    //Obtener vehiculos paginados por defecto inicia en 0 y termina en 8
    @GetMapping("/vehiculos-paginados")
    public Page<VehiculoResponse> vehiuculosPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return vehiculoService.listarVehiculso(page, size);
    }

    //Buscar un vehiculo por placa
    @GetMapping("placa")
    public ResponseEntity<VehiculoResponse> buscarVehiculo(@RequestParam String placa) {
        VehiculoResponse cart = vehiculoService.buscarVehiculoPorPlaca(placa);
        return ResponseEntity.ok(cart);
    }

}
