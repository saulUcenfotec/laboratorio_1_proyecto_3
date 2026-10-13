package ac.cr.cenfotec.laboratorio1.security;
import ac.cr.cenfotec.laboratorio1.model.Usuario;
import ac.cr.cenfotec.laboratorio1.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
/** Configura autenticación JWT, autorización por roles, CORS y protección CSRF. */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    /** Hashea las contraseñas con BCrypt al autenticar y al inicializar usuarios. */
    /** Crea el codificador BCrypt usado para almacenar y verificar contraseñas. */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Busca las cuentas y convierte sus roles en autoridades de Spring Security. */
    /** Carga un usuario y convierte sus roles en autoridades de Spring Security. */
    @Bean
    UserDetailsService userDetailsService(UsuarioRepository repository) {
        return username -> repository.findByUsername(username)
                .map(SecurityConfig::toDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    /** Adapta el usuario de base de datos al formato de autenticación de Spring. */
    private static org.springframework.security.core.userdetails.UserDetails toDetails(Usuario usuario) {
        String[] authorities = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .toArray(String[]::new);

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }

    /** Configura autenticación de usuario y verificación de contraseña BCrypt. */
    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService service, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(service);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    /** Obtiene el gestor de autenticación configurado por Spring. */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /** Define qué peticiones son públicas y conecta el filtro que valida JWT. */
    /** Define las reglas HTTP, CSRF, CORS y el filtro de JWT. */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter,
                                            DaoAuthenticationProvider provider) throws Exception {
        return http
                .cors(cors -> {})
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authenticationProvider(provider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/csrf").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /** Permite llamadas desde frontends locales conocidos durante el desarrollo. */
    /** Configura los orígenes locales permitidos para llamadas desde navegador. */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173", "http://localhost:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-XSRF-TOKEN"));
        config.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
