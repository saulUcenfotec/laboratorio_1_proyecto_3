package ac.cr.cenfotec.laboratorio1.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    /** Constructor requerido por JPA al reconstruir una fila de la base de datos. */
    protected Usuario() {}
    /** Crea una cuenta con su nombre, contraseña cifrada y roles asignados. */
    public Usuario(String username, String password, Set<Rol> roles) { this.username = username; this.password = password; this.roles = roles; }
    /** Devuelve el ID de la cuenta. */
    public Long getId() { return id; }
    /** Devuelve el nombre de usuario. */
    public String getUsername() { return username; }
    /** Devuelve el hash BCrypt de la contraseña. */
    public String getPassword() { return password; }
    /** Devuelve los roles asignados a la cuenta. */
    public Set<Rol> getRoles() { return roles; }
}
