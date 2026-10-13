package ac.cr.cenfotec.laboratorio1.repository;
import ac.cr.cenfotec.laboratorio1.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol, Long> {
    /** Busca un rol por nombre para reutilizarlo durante el inicio de la aplicación. */
    Optional<Rol> findByNombre(String nombre);
}
