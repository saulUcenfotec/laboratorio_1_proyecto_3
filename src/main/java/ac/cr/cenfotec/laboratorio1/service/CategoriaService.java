package ac.cr.cenfotec.laboratorio1.service;

import ac.cr.cenfotec.laboratorio1.dto.CategoriaRequest;
import ac.cr.cenfotec.laboratorio1.model.Categoria;
import ac.cr.cenfotec.laboratorio1.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Contiene las operaciones de negocio para administrar categorías. */
@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    /** Devuelve todas las categorías guardadas. */
    public List<Categoria> listar() {
        return repository.findAll();
    }

    /** Busca por ID y devuelve 404 cuando no hay una categoría con ese ID. */
    public Categoria obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    /** Crea la categoría si el nombre normalizado aún no existe. */
    public Categoria crear(CategoriaRequest request) {
        String nombre = request.nombre().trim();
        if (repository.existsByNombreIgnoreCase(nombre)) {
            throw nombreDuplicado();
        }
        Categoria categoria = new Categoria(nombre, request.descripcion());
        return guardarSinDuplicar(categoria);
    }

    /** Actualiza una categoría y evita repetir el nombre de otra categoría. */
    public Categoria actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = obtener(id);
        String nombre = request.nombre().trim();
        if (repository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw nombreDuplicado();
        }
        categoria.setNombre(nombre);
        categoria.setDescripcion(request.descripcion());
        return guardarSinDuplicar(categoria);
    }

    /** Elimina la categoría indicada, si existe. */
    public void borrar(Long id) {
        Categoria categoria = obtener(id);
        repository.delete(categoria);
    }

    /** Guarda la categoría y convierte conflictos de unicidad en una respuesta 409. */
    private Categoria guardarSinDuplicar(Categoria categoria) {
        try {
            return repository.save(categoria);
        } catch (DataIntegrityViolationException exception) {
            throw nombreDuplicado();
        }
    }

    /** Construye el error HTTP 409 usado cuando el nombre ya está ocupado. */
    private ResponseStatusException nombreDuplicado() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con ese nombre");
    }
}
