package edu.pucmm.isc.objetos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductoVentaProdId implements Serializable {

    @Column(name = "venta_id")
    private long ventaId;

    @Column(name = "producto_id")
    private int productoId;

    protected ProductoVentaProdId() {}

    public ProductoVentaProdId(long ventaId, int productoId) {
        this.ventaId = ventaId;
        this.productoId = productoId;
    }

    public long getVentaId() {
        return ventaId;
    }

    public void setVentaId(long ventaId) {
        this.ventaId = ventaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        ProductoVentaProdId that = (ProductoVentaProdId) o;
        return Objects.equals(ventaId, that.ventaId) && Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ventaId, productoId);
    }
}
