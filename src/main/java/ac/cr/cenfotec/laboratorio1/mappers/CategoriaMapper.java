package ac.cr.cenfotec.laboratorio1.mappers;

import ac.cr.cenfotec.laboratorio1.dto.CategoriaResponse;
import ac.cr.cenfotec.laboratorio1.model.Categoria;
import org.springframework.stereotype.Component;

/** Convierte entidades Categoria en respuestas públicas de la API. */
@Component
public class CategoriaMapper {
    /** Convierte una categoría persistida al formato JSON que recibe el cliente. */
    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}
