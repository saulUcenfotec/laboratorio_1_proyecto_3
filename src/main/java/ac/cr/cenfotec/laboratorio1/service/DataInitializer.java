package ac.cr.cenfotec.laboratorio1.service;
import ac.cr.cenfotec.laboratorio1.model.Rol;
import ac.cr.cenfotec.laboratorio1.model.Usuario;
import ac.cr.cenfotec.laboratorio1.repository.RolRepository;
import ac.cr.cenfotec.laboratorio1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;
/** Inserta los roles y las cuentas requeridas al iniciar, sin crear categorías. */
@Configuration
public class DataInitializer {
    /** Registra roles y cuentas iniciales si todavía no existen en la base de datos. */
    @Bean
    CommandLineRunner initializeUsers(
            RolRepository roles,
            UsuarioRepository users,
            PasswordEncoder encoder,
            @Value("${app.bootstrap.admin.username}") String adminName,
            @Value("${app.bootstrap.admin.password}") String adminPassword,
            @Value("${app.bootstrap.user.username}") String userName,
            @Value("${app.bootstrap.user.password}") String userPassword) {
        return args -> {
            // Los roles se reutilizan para evitar duplicados en cada arranque.
            Rol adminRole = roles.findByNombre("SUPER-ADMIN-ROLE").orElseGet(() -> roles.save(new Rol("SUPER-ADMIN-ROLE")));
            Rol userRole = roles.findByNombre("USER").orElseGet(() -> roles.save(new Rol("USER")));

            // Las contraseñas se guardan hasheadas; nunca se persiste el texto original.
            if (users.findByUsername(adminName).isEmpty()) {
                users.save(new Usuario(adminName, encoder.encode(adminPassword), Set.of(adminRole)));
            }
            if (users.findByUsername(userName).isEmpty()) {
                users.save(new Usuario(userName, encoder.encode(userPassword), Set.of(userRole)));
            }
        };
    }
}
