package planillaje.Vehicular.planillaje.vehicular.servicios;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.RolesEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.PuestoRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.RolRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PuestoRepository puestoRepository;
    private final EmpresaRepository empresaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PuestoRepository puestoRepository, EmpresaRepository empresaRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UsuarioResponse registrarUsuario(UsuarioRequest data) {
        //Buscar empresa y puesto en el que está o va a ser ubicado
        EmpresaEntity empresa = empresaRepository.findById(data.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no registrada"));
        PuestoEntity puesto = puestoRepository.findById(data.getPuestoId()).orElseThrow(() -> new NotFoundException("Puesto no encontrado"));

        //ASIGNAR ROL POR DEFECTO ES RECORREDOR VEHICULAR
        RolesEntity rol = rolRepository.findByRoleName("ROLE_RECORREDOR").orElseThrow(() -> new NotFoundException("Rol no registrado"));

        //Crear el usuario
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nombre(data.getNombre())
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword()))
                .empresa(empresa)
                .puesto(puesto)
                .roles(rol)
                .build();


        UsuarioEntity newUsuario = usuarioRepository.save(usuario);

        return  UsuarioResponse.builder()
                .id(newUsuario.getId())
                .nombre(newUsuario.getNombre())
                .useraname(newUsuario.getUsername())
                .nombreEmpresa(empresa.getNombreEmpresa())
                .puesto(puesto.getNombrePuesto())
                .rol(rol.getRoleName())
                .build();

    }

    /*
    PENDIENTES
    LISTAR TODOS
    BUSCAR UNO
    DESVINCULAR O ELIMINAR
    ACTUALIZAR
     */

}
