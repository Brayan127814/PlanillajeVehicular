package planillaje.Vehicular.planillaje.vehicular.mapper;

import org.springframework.context.annotation.Configuration;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;

@Configuration
public class UsuariosMapper {

    public UsuarioResponse usuarioToResponse(UsuarioEntity usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .username(usuario.getUsername())
                .nombreEmpresa(usuario.getEmpresa().getNombreEmpresa())
                .puesto(usuario.getPuesto().getNombrePuesto())
                .rol(usuario.getRoles().getRoleName())
                .build();
    }
}
