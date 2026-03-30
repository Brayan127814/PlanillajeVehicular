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
    @NotBlank(message = "Numero de pa")
    private String nombrePuesto;
    private  String direcccion;
    private  Long empresaId;
    private  Integer totalParqueaderos;
}
