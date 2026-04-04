package planillaje.Vehicular.planillaje.vehicular.mapper;

import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.dtos.PuestoResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;

@Component
public class PuestoMapper {
    public PuestoResponse puestoResponse(PuestoEntity puesto) {
        return PuestoResponse.builder()
                .id(puesto.getId())
                .nombrePuesto(puesto.getNombrePuesto())
                .direccion(puesto.getDireccion())
                .nombreEmpresa(puesto.getEmpresa().getNombreEmpresa())
                .totalParqueaderos(puesto.getTotalParqueaderos())
                .build();
    }
}
