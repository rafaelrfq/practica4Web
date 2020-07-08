package edu.pucmm.isc.objetos;

import kotlin.collections.ArrayDeque;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Objects;

@Entity(name = "VentaProducto")
@Table(name = "venta_producto")
public class VentasProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se genera el ID automatico
    long id;
    String nombreCliente;
    java.util.Date fechaCompra;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductoVentaProd> listaProductos = new ArrayList<>();

    @Transient
    List<Producto> listaProd = new ArrayList<Producto>();

    //Constructors
    public VentasProducto() { }

    public VentasProducto(Date fechaCompra, String nombreCliente){
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
    }

    public VentasProducto(Date fechaCompra, String nombreCliente, List<ProductoVentaProd> listaProductos) {
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
    }

    //Getters and setters
    public void setId(long id) { this.id = id; }

    public long getId() { return id; }

    public void setFechaCompra(Date fechaCompra) { this.fechaCompra = fechaCompra; }

    public Date getFechaCompra() { return fechaCompra; }

    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreCliente() { return nombreCliente; }

    public void setListaProductos(List<ProductoVentaProd> listaProductos) { this.listaProductos = listaProductos; }

    public List<ProductoVentaProd> getListaProductos() { return listaProductos; }

    public void setListaProd(List<Producto> lista) { this.listaProd = lista; }

    public List<Producto> getListaProd() { return listaProd; }

    public void agregarProducto(Producto producto) {
        ProductoVentaProd prodVenta = new ProductoVentaProd(this, producto);
        listaProductos.add(prodVenta);
        producto.getListaVentas().add(prodVenta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentasProducto venta = (VentasProducto) o;
        return Objects.equals(nombreCliente, venta.nombreCliente)
                && Objects.equals(fechaCompra, venta.fechaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCliente, fechaCompra);
    }
}
