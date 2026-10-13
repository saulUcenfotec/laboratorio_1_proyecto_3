package ac.cr.cenfotec.laboratorio1.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/** Datos de entrada para crear o actualizar una categoría. */
public record CategoriaRequest(@NotBlank @Size(max = 120) String nombre, @Size(max = 500) String descripcion) {}
