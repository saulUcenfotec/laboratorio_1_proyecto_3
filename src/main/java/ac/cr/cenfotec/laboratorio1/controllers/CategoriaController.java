package ac.cr.cenfotec.laboratorio1.controllers;
import ac.cr.cenfotec.laboratorio1.dto.CategoriaRequest;
import ac.cr.cenfotec.laboratorio1.dto.CategoriaResponse;
import ac.cr.cenfotec.laboratorio1.mappers.CategoriaMapper;
import ac.cr.cenfotec.laboratorio1.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/** Recibe las solicitudes HTTP y delega el trabajo a la capa de servicio. */
@RestController @RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;
    private final CategoriaMapper mapper;

    /** Inyecta la lógica de categorías y el conversor de entidades a respuestas. */
    public CategoriaController(CategoriaService service, CategoriaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /** Lista las categorías disponibles para cualquier usuario autenticado. */
    @GetMapping
    public List<CategoriaResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }

    /** Busca una categoría por su identificador o responde 404 si no existe. */
    @GetMapping("/{id}")
    public CategoriaResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(service.obtener(id));
    }

    /** Crea una categoría nueva; solo SUPER-ADMIN-ROLE puede hacerlo. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public CategoriaResponse crear(@Valid @RequestBody CategoriaRequest request) {
        return mapper.toResponse(service.crear(request));
    }

    /** Actualiza todos los campos editables de una categoría existente. */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public CategoriaResponse actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequest request) {
        return mapper.toResponse(service.actualizar(id, request));
    }

    /** Elimina una categoría existente; solo SUPER-ADMIN-ROLE puede hacerlo. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public void borrar(@PathVariable Long id) {
        service.borrar(id);
    }
}
