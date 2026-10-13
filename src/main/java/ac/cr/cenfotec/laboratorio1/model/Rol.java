package ac.cr.cenfotec.laboratorio1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;

    /** Constructor requerido por JPA al reconstruir una fila de la base de datos. */
    protected Rol() {}
    /** Crea un rol con su nombre único. */
    public Rol(String nombre) { this.nombre = nombre; }
    /** Devuelve el ID del rol. */
    public Long getId() { return id; }
    /** Devuelve el nombre del rol. */
    public String getNombre() { return nombre; }
    /** Cambia el nombre del rol. */
    public void setNombre(String nombre) { this.nombre = nombre; }
}
