package planillaje.Vehicular.planillaje.vehicular.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreEmpresa;
    @NotBlank(message = "El nit es obligatorio")
    private String nit;
<<<<<<< HEAD
=======

    @NotBlank
    private String nombre;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
