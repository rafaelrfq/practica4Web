package edu.pucmm.isc.objetos;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "FotoProducto")
@Table(name = "fotoProducto")
public class FotoProducto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se genera el ID automatico
    private int id;

    @ManyToOne
    private Producto producto;

    private String mimeType;

    @Lob
    private String fotoBase64;

    public FotoProducto() { }

    public FotoProducto(Producto producto, String mimeType, String fotoBase64) {
        this.producto = producto;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }

    public String getMimeType() { return mimeType; }

    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getFotoBase64() { return fotoBase64; }

    public void setFotoBase64(String fotoBase64) { this.fotoBase64 = fotoBase64; }
}
