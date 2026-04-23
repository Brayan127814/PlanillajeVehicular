package planillaje.Vehicular.planillaje.vehicular.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planillaje.Vehicular.planillaje.vehicular.enums.NovedadesPlanillaje;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {
    @NotBlank(message = "El campo nombre es obligario")
<<<<<<< HEAD
    private  String nombre;

    @NotBlank(message = "El campo username es obligatorio")
    private  String username;
    @NotBlank(message = "Contreseña obligatoria")
    private String password;

=======
    private String nombre;

    @NotBlank(message = "El campo username es obligatorio")
    private String username;
    @NotBlank(message = "Contreseña obligatoria")
    private String password;

    private  String token;
/*

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
    @NotNull
    @Min(1)
    private  Long empresaId;

    @NotNull
    @Min(1)
    private  Long puestoId;

<<<<<<< HEAD
=======
 */
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
