package ac.cr.cenfotec.laboratorio1.dto;

import java.math.BigDecimal;

/** Datos de producto que la API devuelve al cliente, incluida su categoría. */
/** Datos de producto devueltos por la API, con su categoría incluida. */
public record ProductoResponse(Long id, String nombre, String descripcion, BigDecimal precio,
                               Integer cantidadStock, CategoriaResponse categoria) { }
