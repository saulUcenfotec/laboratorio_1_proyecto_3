package ac.cr.cenfotec.laboratorio1.repository;
import ac.cr.cenfotec.laboratorio1.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {}
