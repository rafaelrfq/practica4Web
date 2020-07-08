package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.*;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreServices {

    private static StoreServices tienda;
    private CarroCompra carrito;
    private boolean usr = false;
    private boolean adm = false;
    private ProductServices productos = ProductServices.getInstance();
    private UserServices usuarios = UserServices.getInstance();
    private SaleServices ventas = SaleServices.getInstance();
    private Intermediary intermediario = Intermediary.getInstance();
    private CommentServices comentarios = CommentServices.getInstance();

    private StoreServices(){

    }

    //Instancia singleton
    public static StoreServices getInstance(){
        if(tienda == null){
            tienda = new StoreServices();
        }
        return tienda;
    }

    public List<Producto> getListaProductos(){
        return productos.findAll();
    }

    public List<VentasProducto> getListaVentas(){
        List<VentasProducto> sales = ventas.findAll();
        for(int i = 0; i < sales.size(); i++){
            List<ProductoVentaProd> relacion = intermediario.findByVenta(sales.get(i).getId());
            for(int j=0; j < relacion.size(); j++){
                Producto prod = productos.findByID(relacion.get(j).getProducto().getId()).get(0);
                prod.setCantidad(relacion.get(j).getCantidad());
                sales.get(i).getListaProd().add(prod);
            }
        }
        return sales;
    }

    public CarroCompra getCarrito() { return carrito; }

    public void setCarrito(CarroCompra cart) { this.carrito = cart; }

    public boolean getUsr() { return usr; }

    public void setUsr(boolean loggeado) { usr = loggeado; }

    public boolean getAdm() { return adm; }

    public void setAdm(boolean admin) { adm = admin; }

    // Productos, lista de productos y comentarios

    public Producto getProductoPorID(int id){
        return productos.findByID(id).get(0);
    }

    public void insertarProductoDB(Producto producto){
        productos.crear(producto);
    }

    public void actualizarProducto(Producto producto) {
        productos.editar(producto);
    }

    public void eliminarProducto(Producto producto){
        productos.eliminar(producto.getId());
    }

    public Comentario getComentarioPorID(int id) { return comentarios.findById(id).get(0); }

    public List<Comentario> getComentariosPorProducto(int id) { return comentarios.findByProductoId(id); }

    public void insertarComentario(Comentario comentario) { comentarios.crear(comentario); }

    public void eliminarComentario(Comentario comentario) { comentarios.eliminar(comentario.getId()); }

    // Carrito

    public Producto getProductoEnCarrito(int id){
        return carrito.getListaProductos().stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public void limpiarCarrito(){
        List<Producto> tmp = new ArrayList<Producto>();
        carrito.setListaProductos(tmp);
    }

    public void procesarVenta(VentasProducto vta, List<Producto> listaProductos){
        VentasProducto entidadVenta = ventas.crear(vta);
        for(int i=0; i<listaProductos.size(); i++){
            ProductoVentaProd relacion = new ProductoVentaProd(entidadVenta, listaProductos.get(i));
            intermediario.insertarRelacion(relacion);
        }
    }

    public Usuario getUsuarioPorNombreUsuario(String usr){
        return usuarios.findByUsername(usr).get(0);
    }

    public List<Usuario> getListaUsuarios() {
        return usuarios.findAll();
    }

    public Usuario loginUsuario(String usuario, String passw){
        Usuario tmp = getUsuarioPorNombreUsuario(usuario);
        if(tmp == null) {
            throw new RuntimeException("Usuario no existente!");
        } else if(tmp.getUsuario().equals("admin") && tmp.getPassword().equals("admin")) {
            adm = true;
            usr = false;
            return tmp;
        } else if(tmp.getUsuario().equals(usuario) && tmp.getPassword().equals(passw)) {
            usr = true;
            adm = false;
            return tmp;
        } else throw new RuntimeException("Password incorrecto!");
    }

    public void logoutUsuario() {
        usr = false;
        adm = false;
    }
}
