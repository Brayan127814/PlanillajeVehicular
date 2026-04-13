package planillaje.Vehicular.planillaje.vehicular.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import planillaje.Vehicular.planillaje.vehicular.entidades.*;
import planillaje.Vehicular.planillaje.vehicular.respositorios.*;

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

          //  if (rolRepository.count() > 0) return;

            // 🔹 Crear empresa
            EmpresaEntity empresa = EmpresaEntity.builder()
                    .nombreEmpresa("Empresa Demo")
                    .nit("123456789")
                    .build();

            empresaRepository.save(empresa);

            // 🔹 Permisos existentes
            PermisosEntity crearInv = permisosRepository.save(
                    PermisosEntity.builder().nombre("CREAR_INVITACION").build()
            );

            PermisosEntity listarUsuarios = permisosRepository.save(
                    PermisosEntity.builder().nombre("LISTAR_USUARIOS").build()
            );

            // 🔹 NUEVOS PERMISOS PARA PUESTOS
            PermisosEntity crearPuesto = permisosRepository.save(
                    PermisosEntity.builder().nombre("CREAR_PUESTO").build()
            );

            PermisosEntity listarPuesto = permisosRepository.save(
                    PermisosEntity.builder().nombre("LISTAR_PUESTO").build()
            );

            // 🔹 Rol ADMIN con TODOS los permisos
            RolesEntity admin = RolesEntity.builder()
                    .roleName("ROLE_ADMIN")
                    .permisos(Set.of(
                            crearInv,
                            listarUsuarios,
                            crearPuesto,   // ✅ NUEVO
                            listarPuesto    // ✅ NUEVO
                    ))
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
}