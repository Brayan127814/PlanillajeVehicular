package planillaje.Vehicular.planillaje.vehicular.servicios;

<<<<<<< HEAD
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;

@Service
public class EmpresaServicio {

    private EmpresaRepository empresaRepository;

    public EmpresaServicio(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /*
    Registrar una nueva empresa
     */

    public EmpresaResponse registrarEmpresas(EmpresaRequest data) {
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.BadRequestException;
import planillaje.Vehicular.planillaje.vehicular.Excepciones.NotFoundException;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaRequest;
import planillaje.Vehicular.planillaje.vehicular.dtos.EmpresaResponse;
import planillaje.Vehicular.planillaje.vehicular.entidades.EmpresaEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.RolesEntity;
import planillaje.Vehicular.planillaje.vehicular.entidades.UsuarioEntity;
import planillaje.Vehicular.planillaje.vehicular.respositorios.EmpresaRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.RolRepository;
import planillaje.Vehicular.planillaje.vehicular.respositorios.UsuarioRepository;

@Service // Indica que esta clase es un servicio de Spring, manejado por el contenedor de IoC
public class EmpresaServicio {

    private EmpresaRepository empresaRepository; // Repositorio para operaciones CRUD de empresas
    private final RolRepository rolRepository; // Repositorio para roles de usuario
    private final PasswordEncoder passwordEncoder; // Codificador de contraseñas (ej. BCrypt)
    private final UsuarioRepository usuarioRepository; // Repositorio para operaciones CRUD de usuarios

    // Constructor para inyección de dependencias (alternativa a @Autowired)
    public EmpresaServicio(EmpresaRepository empresaRepository, RolRepository rolRepository,
                           PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    /*
     * Registrar una nueva empresa junto con su usuario administrador
     * @param data Datos de la empresa y del usuario administrador
     * @return Información de la empresa registrada
     * @throws BadRequestException Si el nombre de empresa o username ya existen
     * @throws NotFoundException Si el rol de administrador no está registrado en el sistema
     */
    public EmpresaResponse registrarEmpresas(EmpresaRequest data) {

        // 1. Validación: evitar duplicados por nombre de empresa
        if (empresaRepository.existsByNombreEmpresa(data.getNombreEmpresa())) {
            throw new BadRequestException("Nombre de empresa ya Existente");
        }

        // 2. Construcción de la entidad Empresa a partir del DTO
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
        EmpresaEntity empresa = EmpresaEntity.builder()
                .nombreEmpresa(data.getNombreEmpresa())
                .nit(data.getNit())
                .build();

<<<<<<< HEAD
        EmpresaEntity guardado = empresaRepository.save(empresa);

=======
        // 3. Persistir la empresa en la base de datos
        EmpresaEntity guardado = empresaRepository.save(empresa);

        // 4. Buscar el rol "ROLE_ADMIN" que se asignará al usuario administrador
        RolesEntity rol = rolRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new NotFoundException("Rol no registrado"));

        // 5. Validación: evitar duplicados por nombre de usuario
        if (usuarioRepository.existsByUsername(data.getUsername())) {
            throw new BadRequestException("Nombre de usuario no disponible");
        }

        // 6. Construcción del usuario administrador asociado a la empresa recién creada
        UsuarioEntity admin = UsuarioEntity.builder()
                .nombre(data.getNombre())
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword())) // Contraseña encriptada
                .empresa(guardado) // Relación: este usuario pertenece a la empresa
                .roles(rol) // Asignación del rol de administrador
                .build();

        // 7. Persistir el usuario administrador en la base de datos
        UsuarioEntity usuarioGuardado = usuarioRepository.save(admin);

        // 8. Construcción y retorno del DTO de respuesta (solo datos de la empresa)
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
        return EmpresaResponse.builder()
                .id(guardado.getId())
                .nombreEmpresa(guardado.getNombreEmpresa())
                .nit(guardado.getNit())
                .build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
