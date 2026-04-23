package planillaje.Vehicular.planillaje.vehicular.config;

<<<<<<< HEAD
=======
import jakarta.servlet.http.HttpServletResponse;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
<<<<<<< HEAD
=======
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import planillaje.Vehicular.planillaje.vehicular.config.JWT.JwtFilter;
<<<<<<< HEAD
//YENI AGUILAR
=======

//YENI AGUILAR
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
public class Security {
    @Configuration
    @EnableWebSecurity

    public static class SecurityConfig {

        private final CustomDetailService customDetailService;
        private final JwtFilter jwtFilter;

        public SecurityConfig(CustomDetailService customDetailService, JwtFilter jwtFilter) {
            this.customDetailService = customDetailService;
            this.jwtFilter = jwtFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(customDetailService);
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(c -> c.disable())
                    .cors(Customizer.withDefaults())
                    .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
<<<<<<< HEAD
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()

                            //EMPRESAS
                            .requestMatchers(HttpMethod.POST,"/empresas/registrar").permitAll()
                            //PUESTOS
                            .requestMatchers(HttpMethod.POST, "/puestos/registrar").permitAll()


                            //PARQUEADEROS
                            .requestMatchers(HttpMethod.POST, "/parqueaderos/registrar").authenticated()
                            .requestMatchers(HttpMethod.GET, "/parqueaderos/libres").authenticated()
                            .requestMatchers(HttpMethod.GET, "/parqueaderos/parqueaderosPaginados").authenticated()

                            //VEHICULOS
                            .requestMatchers(HttpMethod.POST, "/vehiculos/registrar").authenticated()
                            .requestMatchers(HttpMethod.GET,"/vehiculos/vehiculos-paginados").authenticated()
                            .requestMatchers(HttpMethod.GET,"vehiculos/placa").authenticated()
=======
                    .exceptionHandling(exception -> exception
                            .accessDeniedHandler((request, response, ex) -> {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.setContentType("application/json");

                                response.getWriter().write("""
                                            {
                                              "error": "Acceso denegado",
                                              "mensaje": "No tienes permisos para realizar esta acción"
                                            }
                                        """);
                            })
                            .authenticationEntryPoint((request, response, ex) -> {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json");

                                response.getWriter().write("""
                                            {
                                              "error": "No autenticado",
                                              "mensaje": "Debes iniciar sesión"
                                            }
                                        """);
                            })
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST, "/usuarios/registrar").permitAll()
                            .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()

                            //EMPRESAS
                            .requestMatchers(HttpMethod.POST, "/empresas/registrar").permitAll()
                            //PUESTOS
                            //.requestMatchers(HttpMethod.POST, "/puestos/registrar").authenticated()


                            //PARQUEADEROS
                           // .requestMatchers(HttpMethod.POST, "/parqueaderos/registrar").authenticated()
                            .requestMatchers(HttpMethod.GET, "/parqueaderos/libres").authenticated()
                            .requestMatchers(HttpMethod.GET, "/parqueaderos/parqueaderosPaginados").authenticated()
                            .requestMatchers(HttpMethod.POST, "/parqueaderos/liberar/**").authenticated()

                            //VEHICULOS
                            .requestMatchers(HttpMethod.POST, "/vehiculos/registrar").authenticated()
                            .requestMatchers(HttpMethod.GET, "/vehiculos/vehiculos-paginados").authenticated()
                            .requestMatchers(HttpMethod.GET, "/vehiculos/placa").authenticated()
                            .requestMatchers(HttpMethod.GET, "/vehiculos/VehiculoPlaca").authenticated()
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97

                            //ENPOINTS PROTEGIDOS DEL PLANILLAJE
                            .requestMatchers(HttpMethod.POST, "/planillajeVehicular/registrar").authenticated()
                            .requestMatchers(HttpMethod.GET, "/planillajeVehicular/placa").authenticated()
<<<<<<< HEAD
                            .requestMatchers(HttpMethod.GET,"/planillajeVehicular/paginados").authenticated()
=======
                            .requestMatchers(HttpMethod.GET, "/planillajeVehicular/paginados").authenticated()
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
                            .anyRequest().authenticated()

                    ).authenticationProvider(daoAuthenticationProvider())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }
}
