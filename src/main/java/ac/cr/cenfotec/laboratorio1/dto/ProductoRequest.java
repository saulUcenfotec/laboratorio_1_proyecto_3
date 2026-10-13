package ac.cr.cenfotec.laboratorio1.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
/** Datos de entrada para crear o actualizar un producto y vincularlo a una categoría. */
public record ProductoRequest(@NotBlank @Size(max = 150) String nombre, @Size(max = 500) String descripcion, @NotNull @DecimalMin("0.00") BigDecimal precio, @NotNull @PositiveOrZero Integer cantidadStock, @NotNull Long categoriaId) {}
