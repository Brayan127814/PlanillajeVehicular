package planillaje.Vehicular.planillaje.vehicular.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import planillaje.Vehicular.planillaje.vehicular.entidades.*;
import planillaje.Vehicular.planillaje.vehicular.respositorios.*;

import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PermisosRepository permisosRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init() {
        return args -> {

            // ✅ Verificar si ya existen datos (REACTIVA ESTA LÍNEA)
            if (rolRepository.count() > 0) {
                System.out.println("⚠️ Los datos ya existen, omitiendo inicialización");
                return;
            }

            // 🔹 Crear empresa
            EmpresaEntity empresa = EmpresaEntity.builder()
                    .nombreEmpresa("Empresa Demo")
                    .nit("123456789")
                    .build();

            empresaRepository.save(empresa);

            // 🔹 Permisos existentes (con búsqueda previa para evitar duplicados)
            PermisosEntity crearInv = findOrCreatePermiso("CREAR_INVITACION");
            PermisosEntity listarUsuarios = findOrCreatePermiso("LISTAR_USUARIOS");
            PermisosEntity crearPuesto = findOrCreatePermiso("CREAR_PUESTO");
            PermisosEntity listarPuesto = findOrCreatePermiso("LISTAR_PUESTO");

            // 🔹 Rol ADMIN con TODOS los permisos
            RolesEntity admin = RolesEntity.builder()
                    .roleName("ROLE_ADMIN")
                    .permisos(Set.of(crearInv, listarUsuarios, crearPuesto, listarPuesto))
                    .build();

            rolRepository.save(admin);

            // 🔹 Usuario ADMIN
            UsuarioEntity usuario = UsuarioEntity.builder()
                    .nombre("Administrador")
                    .username("admin")
                    .password(passwordEncoder.encode("123456"))
                    .empresa(empresa)
                    .roles(admin)
                    .build();

            usuarioRepository.save(usuario);

            System.out.println("🔥 Datos iniciales creados correctamente con permisos de puestos");
        };
    }

    // Método auxiliar para evitar duplicados
    private PermisosEntity findOrCreatePermiso(String nombre) {
        Optional<PermisosEntity> existing = permisosRepository.findByNombre(nombre);
        return existing.orElseGet(() -> permisosRepository.save(
                PermisosEntity.builder().nombre(nombre).build()
        ));
    }
}