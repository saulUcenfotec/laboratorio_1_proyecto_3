package ac.cr.cenfotec.laboratorio1.dto;

import java.util.Set;

public record UsuarioResponse(Long id, String username, Set<String> roles) {}
