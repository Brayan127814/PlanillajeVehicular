package planillaje.Vehicular.planillaje.vehicular.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoResponse {
    private  Long id;
    private  String placa;
    private  String marca;
    private  Integer numeroDeParqueadero;
}
