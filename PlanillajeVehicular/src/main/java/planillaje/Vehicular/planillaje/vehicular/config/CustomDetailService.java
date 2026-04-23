package planillaje.Vehicular.planillaje.vehicular.config;

<<<<<<< HEAD
=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
@Service
public class CustomDetailService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
<<<<<<< HEAD
=======
        List<GrantedAuthority> authorities = new ArrayList<>();
        //ROL
        authorities.add(new SimpleGrantedAuthority(usuario.getRoles().getRoleName()));
        usuario.getRoles().getPermisos().forEach(permisos -> authorities.add(new SimpleGrantedAuthority(permisos.getNombre())));
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRoles().getRoleName())
                .build();
    }
}
