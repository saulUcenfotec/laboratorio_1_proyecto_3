package ac.cr.cenfotec.laboratorio1.repository;
import ac.cr.cenfotec.laboratorio1.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    /** Comprueba si ya existe una categoría con el nombre, ignorando mayúsculas. */
    boolean existsByNombreIgnoreCase(String nombre);
    /** Comprueba duplicados excluyendo el ID que se está actualizando. */
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
