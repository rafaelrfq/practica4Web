package edu.pucmm.isc.objetos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Comentario")
@Table(name = "comentario")
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contenido;
    private Date fechaCreado;
    private String usuarioComentario;

    @ManyToOne
    private Producto producto;

    public Comentario() { }

    public Comentario(String contenido, String usuarioComentario, Producto prod) {
        this.contenido = contenido;
        this.usuarioComentario = usuarioComentario;
        this.fechaCreado = new Date();
        this.producto = prod;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getContenido() { return contenido; }

    public void setContenido(String contenido) { this.contenido = contenido; }

    public Date getFechaCreado() { return fechaCreado; }

    public void setFechaCreado(Date fechaCreado) { this.fechaCreado = fechaCreado; }

    public String getUsuarioComentario() { return usuarioComentario; }

    public void setUsuarioComentario(String usuarioComentario) { this.usuarioComentario = usuarioComentario; }
}
