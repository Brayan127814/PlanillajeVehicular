package planillaje.Vehicular.planillaje.vehicular.config.JWT;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;
    private static String[] PUBLIC_URL = {

            "/usuarios/login",
            "/empresas/registrar"
    };
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtFilter(JwtUtils jwtUtils, UsuarioRepository usuarioRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        System.out.println("LOG DOCKER - Intentando acceder a: " + requestUri);
        // Usar AntPathMatcher para que /usuarios/** funcione de verdad
        boolean isPublic = Arrays.stream(PUBLIC_URL)
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));

        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extraer el token del header
        String token = authHeader.substring(7);
        // Validar token
        if (!jwtUtils.validarToken(token)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no valido");
            return;
        }

        // EXTRAER EL SUBJECT
        try {
            String subject = jwtUtils.getSubjectFromToken(token);
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // BUSCAR USUARIO
                UsuarioEntity usuario = usuarioRepository.findByUsername(subject)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(usuario.getRoles().getRoleName()));
                usuario.getRoles().getPermisos().forEach(permisos -> authorities.add(new SimpleGrantedAuthority(permisos.getNombre())));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(), null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no valido");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
