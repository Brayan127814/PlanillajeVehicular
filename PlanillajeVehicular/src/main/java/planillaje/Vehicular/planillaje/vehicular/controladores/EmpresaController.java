package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
import planillaje.Vehicular.planillaje.vehicular.servicios.EmpresaServicio;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private EmpresaServicio empresaServicio;

    public EmpresaController(EmpresaServicio empresaServicio){
         this.empresaServicio = empresaServicio;

    }
    //REGISTRAR EMPRESA
    @PostMapping("/registrar")
    public ResponseEntity<EmpresaResponse> registrar(@Valid @RequestBody EmpresaRequest data){
          EmpresaResponse empresaResponse = empresaServicio.registrarEmpresas(data);
          return  ResponseEntity.ok(empresaResponse);
    }
}
