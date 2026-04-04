package planillaje.Vehicular.planillaje.vehicular.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class CustomDetailService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        //ROL
        authorities.add(new SimpleGrantedAuthority(usuario.getRoles().getRoleName()));
        usuario.getRoles().getPermisos().forEach(permisos -> authorities.add(new SimpleGrantedAuthority(permisos.getNombre())));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRoles().getRoleName())
                .build();
    }
}
