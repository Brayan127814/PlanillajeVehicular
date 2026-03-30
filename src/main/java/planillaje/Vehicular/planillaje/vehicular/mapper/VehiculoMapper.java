package planillaje.Vehicular.planillaje.vehicular.mapper;

import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.dtos.VehiculoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.VehiculoEntity;

@Component
public class VehiculoMapper {

    public VehiculoResponse vehiculoToResponse(VehiculoEntity v) {
        return VehiculoResponse.builder()
                .id(v.getId())
                .placa(v.getPlaca())
                .marca(v.getMarca())
                .numeroDeParqueadero(v.getParqueadero().getNumeroParqueadero())

                .build();
    }
}
