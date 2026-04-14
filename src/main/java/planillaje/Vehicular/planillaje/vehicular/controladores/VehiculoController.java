package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('CREAR_VEHICULO')")
    @PostMapping("/registrar")
    public ResponseEntity<VehiculoResponse> registrar(@Valid @RequestBody VehiculoRequest data) {
        VehiculoResponse vehiculoResponse = vehiculoService.registrarVehiculo(data);
        return ResponseEntity.ok(vehiculoResponse);
    }

    //Obtener vehiculos paginados por defecto inicia en 0 y termina en 8
    @PreAuthorize("hasAuthority('LISTAR_VEHICULO')")
    @GetMapping("/vehiculos-paginados")
    public Page<VehiculoResponse> vehiuculosPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return vehiculoService.listarVehiculso(page, size);
    }

    //Buscar un vehiculo por placa
    @PreAuthorize("hasAuthority('LISTAR_PLACA')")
    @GetMapping("placa")
    public ResponseEntity<VehiculoResponse> buscarVehiculo(@RequestParam String placa) {
        VehiculoResponse cart = vehiculoService.buscarVehiculoPorPlaca(placa);
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAuthority('LISTAR_PLACA')")
    @GetMapping("/VehiculoPlaca")
    public ResponseEntity<VehiculoResponse> buscarPlaca(@RequestParam String placa) {

        VehiculoResponse vehiculoResponse = vehiculoService.buscarPlaca(placa);

        return ResponseEntity.ok(vehiculoResponse);
    }
}
