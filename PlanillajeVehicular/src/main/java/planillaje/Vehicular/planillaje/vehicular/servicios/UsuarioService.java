package planillaje.Vehicular.planillaje.vehicular.servicios;

<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.PuestoEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.RolesEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
=======
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.BadRequestException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.UsuarioResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.*;
import planillaje.Vehicular.planillaje.vehicular.mapper.UsuariosMapper;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
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
<<<<<<< HEAD

    public UsuarioService(UsuarioRepository usuarioRepository, PuestoRepository puestoRepository, EmpresaRepository empresaRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
=======
    private final UsuariosMapper usuariosMapper;
    private final CurrentService currentService;
    private final InvitacionServices invitacionServices;

    public UsuarioService(UsuarioRepository usuarioRepository, PuestoRepository puestoRepository, EmpresaRepository empresaRepository,
                          RolRepository rolRepository, PasswordEncoder passwordEncoder, UsuariosMapper usuariosMapper, CurrentService currentService, InvitacionServices invitacionServices) {
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
        this.usuarioRepository = usuarioRepository;
        this.puestoRepository = puestoRepository;
        this.empresaRepository = empresaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
<<<<<<< HEAD
    }


    public UsuarioResponse registrarUsuario(UsuarioRequest data) {
        //Buscar empresa y puesto en el que está o va a ser ubicado
        EmpresaEntity empresa = empresaRepository.findById(data.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no registrada"));
        PuestoEntity puesto = puestoRepository.findById(data.getPuestoId()).orElseThrow(() -> new NotFoundException("Puesto no encontrado"));
=======
        this.usuariosMapper = usuariosMapper;
        this.currentService = currentService;
        this.invitacionServices = invitacionServices;
    }

    @Transactional
    public UsuarioResponse registrarUsuario(UsuarioRequest data) {
        //Buscar empresa y puesto en el que está o va a ser ubicado
        InvitacionEntity invitacion = invitacionServices.validarInvitacion(data.getToken());
        EmpresaEntity empresa = empresaRepository.findById(invitacion.getEmpresaId()).orElseThrow(() -> new NotFoundException("Empresa no registrada"));
        PuestoEntity puesto = puestoRepository.findById(invitacion.getPuestoId()).orElseThrow(() -> new NotFoundException("Puesto no resgistrado"));
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

        //ASIGNAR ROL POR DEFECTO ES RECORREDOR VEHICULAR
        RolesEntity rol = rolRepository.findByRoleName("ROLE_RECORREDOR").orElseThrow(() -> new NotFoundException("Rol no registrado"));

<<<<<<< HEAD
=======

        if (usuarioRepository.existsByUsername(data.getUsername())) {
            throw new BadRequestException("Usuario no disponible");
        }

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
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

<<<<<<< HEAD
        return  UsuarioResponse.builder()
                .id(newUsuario.getId())
                .nombre(newUsuario.getNombre())
                .useraname(newUsuario.getUsername())
                .nombreEmpresa(empresa.getNombreEmpresa())
                .puesto(puesto.getNombrePuesto())
                .rol(rol.getRoleName())
                .build();

    }

=======
        //marcar invintación como usada
        invitacionServices.marcarComoUsada(invitacion);

        return usuariosMapper.usuarioToResponse(newUsuario);

    }

    //LISTAR TODOS LOS USUARIOS DE UN PUESTO
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

>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
