package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

@Component
public class CurrentService {
    private  final UsuarioRepository usuarioRepository;

    public  CurrentService (UsuarioRepository usuarioRepository){
          this.usuarioRepository = usuarioRepository;
    }

public UsuarioEntity getCurrentUsuario(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            throw new RuntimeException("Usuario no leoguedo");
     }
     return  usuarioRepository.findByUsername(authentication.getName()).orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
}
}
