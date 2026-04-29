package planillaje.Vehicular.planillaje.vehicular.dtos;


import lombok.Builder;
import lombok.Data;
import planillaje.Vehicular.planillaje.vehicular.enums.NovedadesPlanillaje;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PlanillajeResponse {
     private  Long id;
     private  String nombrePuesto;
     private String nombreUsuario;
     private  String vehiculo;
     private  String placa;
     private  Integer parqueadero;
     private NovedadesPlanillaje novedadesPlanillaje;
     private  String detalles;
     private LocalDateTime horaInicio;
     private List<String> fotosBase64;
     
}
