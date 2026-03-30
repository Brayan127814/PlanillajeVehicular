package planillaje.Vehicular.planillaje.vehicular.mapper;

import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.dtos.ParqueaderoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.ParqueaderoEntity;

@Component
public class ParqueaderoMapper {

     public ParqueaderoResponse toResponse(ParqueaderoEntity parqueadero){
          return  ParqueaderoResponse.builder()
                  .id(parqueadero.getId())
                  .numeroParqueadero(parqueadero.getNumeroParqueadero())
                  .estado(parqueadero.getEstado())
                  .nombrePuesto(parqueadero.getPuesto().getNombrePuesto())
                  .build();
     }
}
