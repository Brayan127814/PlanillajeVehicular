package planillaje.Vehicular.planillaje.vehicular.mapper;

import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.dtos.PlanillajeResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.PlanillajeVehicularEntity;

@Component
public class PlanillajeMapper {

    public PlanillajeResponse planillajeToResponse(PlanillajeVehicularEntity p) {
            return  PlanillajeResponse.builder()
                    .id(p.getId())
                    .nombrePuesto(p.getPuesto().getNombrePuesto())
                    .nombreUsuario(p.getUsuario().getNombre())
                    .vehiculo(p.getVehiculo().getMarca())
                    .placa(p.getVehiculo().getPlaca())
                    .parqueadero(p.getParqueadero().getNumeroParqueadero())
                    .novedadesPlanillaje(p.getNovedades())
                    .detalles(p.getDetalle())
                    .horaInicio(p.getHoraInicio())
                    .fotosBase64(p.getFotos().stream().map(f -> f.getFotoBase64()).toList())
                    .build();
    }
}
