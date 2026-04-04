package planillaje.Vehicular.planillaje.vehicular.servicios;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.RolesEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.mapper.UsuariosMapper;
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
    private final UsuariosMapper usuariosMapper;
    private final CurrentService currentService;

    public UsuarioService(UsuarioRepository usuarioRepository, PuestoRepository puestoRepository, EmpresaRepository empresaRepository,
                          RolRepository rolRepository, PasswordEncoder passwordEncoder, UsuariosMapper usuariosMapper, CurrentService currentService) {
        this.usuarioRepository = usuarioRepository;
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuariosMapper = usuariosMapper;
        this.currentService = currentService;
    }

    @Transactional
    public UsuarioResponse registrarUsuario(UsuarioRequest data) {
        //Buscar empresa y puesto en el que está o va a ser ubicado
        EmpresaEntity empresa = empresaRepository.findById(data.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no registrada"));
        PuestoEntity puesto = puestoRepository.findById(data.getPuestoId()).orElseThrow(() -> new NotFoundException("Puesto no encontrado"));

        //ASIGNAR ROL POR DEFECTO ES RECORREDOR VEHICULAR
        RolesEntity rol = rolRepository.findByRoleName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Rol no registrado"));

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

        return usuariosMapper.usuarioToResponse(newUsuario);

    }

    @Transactional
    public Page<UsuarioResponse> listarUnidades(Long puestoId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        UsuarioEntity usuario = currentService.getCurrentUsuario();
        return usuarioRepository.findByPuesto_IdAndEmpresa_Id(puestoId, usuario.getEmpresa().getId(), pageable).map(usuariosMapper::usuarioToResponse);

    }

    /*
    PENDIENTES
    LISTAR TODOS
    BUSCAR UNO
    DESVINCULAR O ELIMINAR
    ACTUALIZAR
     */

}
