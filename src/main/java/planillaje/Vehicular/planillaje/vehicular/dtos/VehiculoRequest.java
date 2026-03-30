package planillaje.Vehicular.planillaje.vehicular.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoRequest {
    @NotBlank(message = "El campo placa es requerido")
    @Pattern(
            regexp = "^[A-Za-z0-9]{5,6}$",
            message = "La placa debe tener entre 5 y 6 caracteres alfanuméricos"
    )
    private String placa;

    @NotBlank(message = "El campo marca es requerido")
    private String marca;

    @NotNull
    private Long parqueaderoId;

}
