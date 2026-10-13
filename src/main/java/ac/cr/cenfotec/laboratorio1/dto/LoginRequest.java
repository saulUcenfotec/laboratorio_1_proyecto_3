package ac.cr.cenfotec.laboratorio1.dto;
import jakarta.validation.constraints.NotBlank;
/** Credenciales requeridas por el endpoint de inicio de sesión. */
public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
