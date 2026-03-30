package planillaje.Vehicular.planillaje.vehicular.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planillaje.Vehicular.planillaje.vehicular.enums.NovedadesPlanillaje;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PlanillajeRequest {

    private String placa;
    private NovedadesPlanillaje novedadesPlanillaje;
    private  String detalle;
    private List<String> fotoBase64;
}
