package planillaje.Vehicular.planillaje.vehicular.controladores;

import jakarta.validation.Valid;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.config.JWT.JwtUtils;
import planillaje.Vehicular.planillaje.vehicular.dtos.LoginRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;
import planillaje.Vehicular.planillaje.vehicular.servicios.UsuarioService;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, JwtUtils jwtUtils) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest data) {
        UsuarioResponse usuario = usuarioService.registrarUsuario(data);
        return ResponseEntity.ok(usuario);
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword())
            );


            String username = authentication.getName();
            //BUscar elusuario
            UsuarioEntity usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

            //GENERAR TOKENS
            String accessToken = jwtUtils.generarToken(usuario);
            String refreshToken = jwtUtils.refreshToken(usuario);
            return ResponseEntity.ok(Map.of("AccessToken", accessToken, "RefreshToken", refreshToken));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
    }
<<<<<<< HEAD
=======

    //LISTAR USUARIOS
    @PreAuthorize("hasAuthority('LISTAR_USUARIOS')")
    @GetMapping("/{puestoId}")
    public Page<UsuarioResponse> listar(@PathVariable Long puestoId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        return usuarioService.listarUnidades(puestoId, page, size);
    }
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
