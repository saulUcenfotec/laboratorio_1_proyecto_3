package ac.cr.cenfotec.laboratorio1.controllers;
import ac.cr.cenfotec.laboratorio1.dto.ProductoRequest;
import ac.cr.cenfotec.laboratorio1.dto.ProductoResponse;
import ac.cr.cenfotec.laboratorio1.mappers.ProductoMapper;
import ac.cr.cenfotec.laboratorio1.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/** Recibe las solicitudes HTTP de productos y delega el trabajo al servicio. */
@RestController @RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;
    private final ProductoMapper mapper;

    /** Inyecta la lógica de productos y el conversor a respuestas de API. */
    public ProductoController(ProductoService service, ProductoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /** Lista los productos disponibles para cualquier usuario autenticado. */
    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }

    /** Busca un producto por su identificador o responde 404 si no existe. */
    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return mapper.toResponse(service.obtener(id));
    }

    /** Crea un producto asociado a una categoría; requiere rol administrador. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ProductoResponse crear(@Valid @RequestBody ProductoRequest request) {
        return mapper.toResponse(service.crear(request));
    }

    /** Reemplaza los datos editables de un producto existente. */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return mapper.toResponse(service.actualizar(id, request));
    }

    /** Elimina un producto existente; requiere rol administrador. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public void borrar(@PathVariable Long id) {
        service.borrar(id);
    }
}
