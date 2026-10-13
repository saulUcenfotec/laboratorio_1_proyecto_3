package ac.cr.cenfotec.laboratorio1.service;

import ac.cr.cenfotec.laboratorio1.dto.ProductoRequest;
import ac.cr.cenfotec.laboratorio1.model.Categoria;
import ac.cr.cenfotec.laboratorio1.model.Producto;
import ac.cr.cenfotec.laboratorio1.repository.CategoriaRepository;
import ac.cr.cenfotec.laboratorio1.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Contiene las operaciones de negocio para administrar productos. */
@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /** Devuelve todos los productos guardados. */
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    /** Busca un producto por ID y devuelve 404 si no existe. */
    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    /** Crea un producto después de comprobar que su categoría existe. */
    public Producto crear(ProductoRequest request) {
        Producto producto = new Producto(
                request.nombre(), request.descripcion(), request.precio(),
                request.cantidadStock(), obtenerCategoria(request.categoriaId())
        );
        return productoRepository.save(producto);
    }

    /** Actualiza un producto y valida la categoría asociada. */
    public Producto actualizar(Long id, ProductoRequest request) {
        Producto producto = obtener(id);
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setCantidadStock(request.cantidadStock());
        producto.setCategoria(obtenerCategoria(request.categoriaId()));
        return productoRepository.save(producto);
    }

    /** Elimina el producto indicado, si existe. */
    public void borrar(Long id) {
        productoRepository.delete(obtener(id));
    }

    /** Busca la categoría asociada y devuelve 404 si el ID no es válido. */
    private Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }
}
