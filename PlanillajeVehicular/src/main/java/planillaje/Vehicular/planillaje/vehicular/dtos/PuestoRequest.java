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
<<<<<<< HEAD
    @NotBlank(message = "Numero de pa")
    private String nombrePuesto;
    private  String direcccion;
    private  Long empresaId;
=======
    @NotBlank(message = "Numero de puesto obligatorio")
    private String nombrePuesto;
    @NotBlank(message = "Dirección obligatoria")
    private  String direccion;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    private  Integer totalParqueaderos;
}
