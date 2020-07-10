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

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<ProductoVentaProd> listaVentas = new ArrayList<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<Comentario> listaComentarios;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<FotoProducto> listaFotos;

    //Constructors
    public Producto() { }

    //Constructor sin cantidad (para agregar al listado)
    public Producto(int id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, BigDecimal precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
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

    public List<ProductoVentaProd> getListaVentas() { return listaVentas; }

    public void setListaVentas(List<ProductoVentaProd> listaVentas) { this.listaVentas = listaVentas; }

    public Set<Comentario> getListaComentarios() { return listaComentarios; }

    public void setListaComentarios(Set<Comentario> listaComentarios) { this.listaComentarios = listaComentarios; }

    public Set<FotoProducto> getListaFotos() { return listaFotos; }

    public void setListaFotos(Set<FotoProducto> listaFotos) { this.listaFotos = listaFotos; }

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
