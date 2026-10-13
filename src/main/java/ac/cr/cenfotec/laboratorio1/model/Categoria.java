package ac.cr.cenfotec.laboratorio1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(name = "uk_categorias_nombre", columnNames = "nombre"))
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;
    @Column(length = 500)
    private String descripcion;

    /** Constructor requerido por JPA al reconstruir una fila de la base de datos. */
    protected Categoria() {}
    /** Crea una categoría nueva con nombre y descripción. */
    public Categoria(String nombre, String descripcion) { this.nombre = nombre; this.descripcion = descripcion; }
    /** Devuelve el ID asignado por la base de datos. */
    public Long getId() { return id; }
    /** Devuelve el nombre de la categoría. */
    public String getNombre() { return nombre; }
    /** Cambia el nombre de la categoría. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve la descripción de la categoría. */
    public String getDescripcion() { return descripcion; }
    /** Cambia la descripción de la categoría. */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
