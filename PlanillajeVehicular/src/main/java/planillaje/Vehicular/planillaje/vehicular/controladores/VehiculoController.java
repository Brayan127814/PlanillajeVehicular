package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
=======
import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
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

<<<<<<< HEAD
=======
    @PreAuthorize("hasAuthority('CREAR_VEHICULO')")
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    @PostMapping("/registrar")
    public ResponseEntity<VehiculoResponse> registrar(@Valid @RequestBody VehiculoRequest data) {
        VehiculoResponse vehiculoResponse = vehiculoService.registrarVehiculo(data);
        return ResponseEntity.ok(vehiculoResponse);
    }

    //Obtener vehiculos paginados por defecto inicia en 0 y termina en 8
<<<<<<< HEAD
=======
    @PreAuthorize("hasAuthority('LISTAR_VEHICULO')")
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    @GetMapping("/vehiculos-paginados")
    public Page<VehiculoResponse> vehiuculosPaginados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return vehiculoService.listarVehiculso(page, size);
    }

    //Buscar un vehiculo por placa
<<<<<<< HEAD
=======
    @PreAuthorize("hasAuthority('LISTAR_PLACA')")
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    @GetMapping("placa")
    public ResponseEntity<VehiculoResponse> buscarVehiculo(@RequestParam String placa) {
        VehiculoResponse cart = vehiculoService.buscarVehiculoPorPlaca(placa);
        return ResponseEntity.ok(cart);
    }

<<<<<<< HEAD
=======
    @PreAuthorize("hasAuthority('LISTAR_PLACA')")
    @GetMapping("/VehiculoPlaca")
    public ResponseEntity<VehiculoResponse> buscarPlaca(@RequestParam String placa) {

        VehiculoResponse vehiculoResponse = vehiculoService.buscarPlaca(placa);

        return ResponseEntity.ok(vehiculoResponse);
    }
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
