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
<<<<<<< HEAD
    private  String useraname;
=======
    private  String username;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    private  String nombreEmpresa;
    private  String puesto;
    private String rol;
}
