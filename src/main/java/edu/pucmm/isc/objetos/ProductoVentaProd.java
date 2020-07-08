package edu.pucmm.isc.objetos;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ProductoVentaProd")
@Table(name = "producto_venta_prod")
public class ProductoVentaProd {

    @EmbeddedId
    private ProductoVentaProdId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ventaId")
    private VentasProducto venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;

    protected ProductoVentaProd() {}

    public ProductoVentaProd(VentasProducto venta, Producto producto) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = producto.getCantidad();
        this.id = new ProductoVentaProdId(venta.getId(), producto.getId());
    }

    // Getters and Setters

    public ProductoVentaProdId getId() {
        return id;
    }

    public void setId(ProductoVentaProdId id) {
        this.id = id;
    }

    public VentasProducto getVenta() {
        return venta;
    }

    public void setVenta(VentasProducto venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ProductoVentaProd that = (ProductoVentaProd) o;
        return Objects.equals(venta, that.venta) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venta, producto);
    }
}