package edu.pucmm.isc;
import edu.pucmm.isc.controladores.UserController;
import edu.pucmm.isc.controladores.TemplateController;
import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.Usuario;
import edu.pucmm.isc.servicios.*;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws SQLException {

        // Se inicia la base de datos
        DataBaseManagement.getInstance().startDB();

        // Se prueba la conexion con la DB
        ConexionDB.getInstance().testConn();

        // Creacion de usuarios predeterminados
        Usuario usr1 = new Usuario("rafael", "Rafael Felipe", "0712");
        Usuario usr2 = new Usuario("admin", "Administrador", "admin");
        UserServices.getInstance().crear(usr1);
        UserServices.getInstance().crear(usr2);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("/public"); //Agregamos carpeta public como source de archivos estaticos
            javalinConfig.registerPlugin(new RouteOverviewPlugin("rutas")); //Aplicamos el plugin de rutas
        }).start(7000);

        System.out.println("Servidor corriendo en: http://localhost:7000/");

        //Creacion del manejador
        app.get("/", ctx -> {
            List<Producto> productosIniciales = new ArrayList<Producto>();
            ctx.sessionAttribute("carrito", new CarroCompra(1, productosIniciales));
            ctx.redirect("/api/productos/listar/");
        });

        //Manejadores de rutas
        new UserController(app).aplicarRutas();
        new TemplateController(app).aplicarRutas();

        // Se detiene la base de datos
        //DataBaseManagement.getInstance().stopDB();
    }
}