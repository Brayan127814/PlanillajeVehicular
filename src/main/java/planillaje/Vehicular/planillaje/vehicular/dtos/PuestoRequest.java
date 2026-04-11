package planillaje.Vehicular.planillaje.vehicular.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuestoRequest {
    @NotBlank(message = "Numero de puesto obligatorio")
    private String nombrePuesto;
    @NotBlank(message = "Dirección obligatoria")
    private  String direccion;
    private  Integer totalParqueaderos;
}
