package ac.cr.cenfotec.laboratorio1.dto;

/** Datos de categoría que la API devuelve al cliente. */
/** Datos de categoría devueltos al cliente; no expone la entidad JPA. */
public record CategoriaResponse(Long id, String nombre, String descripcion) { }
