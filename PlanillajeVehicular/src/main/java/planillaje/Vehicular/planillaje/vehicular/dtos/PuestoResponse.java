package planillaje.Vehicular.planillaje.vehicular.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PuestoResponse {
    private  Long id;
    private String nombrePuesto;
    private String direccion;
    private  String nombreEmpresa;
    private  Integer totalParqueaderos;
}
