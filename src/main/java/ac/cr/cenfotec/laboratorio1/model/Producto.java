package ac.cr.cenfotec.laboratorio1.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nombre;
    @Column(length = 500)
    private String descripcion;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;
    @Column(nullable = false)
    private Integer cantidadStock;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /** Constructor requerido por JPA al reconstruir una fila de la base de datos. */
    protected Producto() {}
    /** Crea un producto con sus datos y la categoría a la que pertenece. */
    public Producto(String nombre, String descripcion, BigDecimal precio, Integer cantidadStock, Categoria categoria) { this.nombre = nombre; this.descripcion = descripcion; this.precio = precio; this.cantidadStock = cantidadStock; this.categoria = categoria; }
    /** Devuelve el ID asignado por la base de datos. */
    public Long getId() { return id; }
    /** Devuelve el nombre del producto. */
    public String getNombre() { return nombre; }
    /** Cambia el nombre del producto. */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /** Devuelve la descripción del producto. */
    public String getDescripcion() { return descripcion; }
    /** Cambia la descripción del producto. */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    /** Devuelve el precio del producto. */
    public BigDecimal getPrecio() { return precio; }
    /** Cambia el precio del producto. */
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    /** Devuelve las unidades disponibles. */
    public Integer getCantidadStock() { return cantidadStock; }
    /** Cambia la cantidad disponible. */
    public void setCantidadStock(Integer cantidadStock) { this.cantidadStock = cantidadStock; }
    /** Devuelve la categoría asociada. */
    public Categoria getCategoria() { return categoria; }
    /** Asocia el producto a otra categoría existente. */
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
