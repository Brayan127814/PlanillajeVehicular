package planillaje.Vehicular.planillaje.vehicular.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private  String useraname;
    private  String nombreEmpresa;
    private  String puesto;
    private String rol;
}
