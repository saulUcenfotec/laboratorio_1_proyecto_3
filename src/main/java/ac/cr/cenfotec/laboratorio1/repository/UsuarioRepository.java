package ac.cr.cenfotec.laboratorio1.repository;
import ac.cr.cenfotec.laboratorio1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /** Busca una cuenta por username para login y carga inicial de usuarios. */
    Optional<Usuario> findByUsername(String username);
}
