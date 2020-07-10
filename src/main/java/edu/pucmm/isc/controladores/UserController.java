package edu.pucmm.isc.controladores;
import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.Usuario;
import edu.pucmm.isc.servicios.StoreServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    private Javalin app;
    public UserController(Javalin app){
        this.app = app;
    }

    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }

    //Obtencion de instacia de tienda
    StoreServices tienda = StoreServices.getInstance();

    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion y si la cookie recordarme existe antes de cargar
            before(ctx -> {
                // Verificamos si existe el cookie de recordarme y si existe loggeamos automaticamente al usuario al que le pertenece
                if(ctx.cookie("recordarme") != null){
                    //ctx.req.changeSessionId();
                    Usuario usrCookie = tienda.revisarCookie(ctx.cookie("recordarme"));
                    if(usrCookie != null){
                        tienda.loginUsuario(usrCookie.getUsuario(), usrCookie.getPassword(), ctx.req.getSession().getId(), ctx.cookie("recordarme"));
                    }
                }
            });

            path("/api/usuarios/", () -> {

                // Render de Login de usuarios
                // http://localhost:7000/api/usuarios/login/
                get("/login/", ctx -> {
                    if(tienda.isUsr() || tienda.isAdm()) {
                        ctx.redirect("/");
                    }
                    CarroCompra carrito = ctx.sessionAttribute("carrito");
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("usr", tienda.isUsr());
                    contexto.put("admin", tienda.isAdm());
                    contexto.put("title", "Login de Usuario");
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    ctx.render("/public/templates/login.ftl", contexto);
                });

                // Manejo de Login de usuarios
                // http://localhost:7000/api/usuarios/login/
                post("/login/", ctx -> {
                    String usr = ctx.formParam("usuario");
                    String passw = ctx.formParam("password");
                    String check = ctx.formParam("recordarme");
                    String encryptedPassword = null;
                    ctx.req.changeSessionId();
                    if(check != null && check.equals("yes")){
                        String key = "@cm1ptgrone" + ctx.req.getSession().getId();
                        StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
                        encryptedPassword = pwEncryptor.encryptPassword(key);
                        ctx.cookie("recordarme", encryptedPassword, 604800);
                    }
                    Usuario tmp = tienda.loginUsuario(usr, passw, ctx.req.getSession().getId(), encryptedPassword);
                    ctx.redirect("/");
                });

                // Logout de usuarios
                // http://localhost:7000/api/usuarios/logout/
                get("/logout/", ctx -> {
                    tienda.logoutUsuario(ctx.req.getSession().getId(), ctx.cookie("recordarme"));
                    ctx.req.getSession().invalidate();
                    ctx.removeCookie("recordarme");
                    ctx.redirect("/api/usuarios/login/");
                });
            });
        });
    }
}