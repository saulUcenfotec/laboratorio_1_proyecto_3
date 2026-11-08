package ac.cr.cenfotec.laboratorio1.controllers;

import ac.cr.cenfotec.laboratorio1.dto.RegistroRequest;
import ac.cr.cenfotec.laboratorio1.dto.UsuarioResponse;
import ac.cr.cenfotec.laboratorio1.model.Usuario;
import ac.cr.cenfotec.laboratorio1.repository.RolRepository;
import ac.cr.cenfotec.laboratorio1.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarios, RolRepository roles, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.roles = roles;
        this.encoder = encoder;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@Valid @RequestBody RegistroRequest request) {
        if (usuarios.findByUsername(request.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está registrado");
        }
        var userRole = roles.findByNombre("USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol USER no configurado"));
        return toResponse(usuarios.save(new Usuario(request.username(), encoder.encode(request.password()), Set.of(userRole))));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    public List<UsuarioResponse> listar() {
        return usuarios.findAll().stream().map(UsuarioController::toResponse).toList();
    }

    private static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getUsername(),
                usuario.getRoles().stream().map(rol -> rol.getNombre()).collect(java.util.stream.Collectors.toSet()));
    }
}
