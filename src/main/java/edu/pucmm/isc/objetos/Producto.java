package edu.pucmm.isc.objetos;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Producto")
@Table(name = "producto")
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se genera el ID automatico
    private int id;

    @NaturalId(mutable = true)
    private String nombre;

    private BigDecimal precio;
    private int cantidad;
    private String descripcion;
    private String mimeType;
    @Lob
    private String fotoBase64;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ProductoVentaProd> listaVentas = new ArrayList<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<Comentario> listaComentarios;

    //Constructors
    public Producto() { }

    //Constructor sin cantidad (para agregar al listado)
    public Producto(int id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, BigDecimal precio, String descripcion, String mimeType, String foto) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.mimeType = mimeType;
        this.fotoBase64 = foto;
    }

    //Constructor con cantidad (para agregar a carrito y posteriormente a ventas)
    public Producto(int id, String nombre, BigDecimal precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    //Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }

    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getCantidad() { return cantidad; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMimeType() { return mimeType; }

    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getFotoBase64() { return fotoBase64; }

    public void setFotoBase64(String fotoBase64) { this.fotoBase64 = fotoBase64; }

    public List<ProductoVentaProd> getListaVentas() { return listaVentas; }

    public void setListaVentas(List<ProductoVentaProd> listaVentas) { this.listaVentas = listaVentas; }

    public Set<Comentario> getListaComentarios() { return listaComentarios; }

    public void setListaComentarios(Set<Comentario> listaComentarios) { this.listaComentarios = listaComentarios; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre) &&
                Objects.equals(precio, producto.precio) &&
                Objects.equals(cantidad, producto.cantidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, precio, cantidad);
    }
}
