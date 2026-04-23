package planillaje.Vehicular.planillaje.vehicular.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planillaje.Vehicular.planillaje.vehicular.enums.ParqueaderoEstado;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParqueaderoResponse {
     private Long id;
     private  Integer numeroParqueadero;
     private ParqueaderoEstado estado;
     private  String nombrePuesto;

}
