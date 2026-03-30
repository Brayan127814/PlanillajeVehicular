package planillaje.Vehicular.planillaje.vehicular.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreEmpresa;
    @NotBlank(message = "El nit es obligatorio")
    private String nit;
}
