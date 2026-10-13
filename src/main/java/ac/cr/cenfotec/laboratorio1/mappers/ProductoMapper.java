package ac.cr.cenfotec.laboratorio1.mappers;

import ac.cr.cenfotec.laboratorio1.dto.ProductoResponse;
import ac.cr.cenfotec.laboratorio1.model.Producto;
import org.springframework.stereotype.Component;

/** Convierte entidades Producto y su categoría a una respuesta JSON. */
@Component
public class ProductoMapper {
    private final CategoriaMapper categoriaMapper;

    /** Crea el mapper e inyecta el conversor usado para la categoría del producto. */
    public ProductoMapper(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }

    /** Convierte un producto y su categoría relacionada en una respuesta de API. */
    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                categoriaMapper.toResponse(producto.getCategoria())
        );
    }
}
