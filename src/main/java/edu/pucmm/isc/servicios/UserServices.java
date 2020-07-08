package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserServices extends EntityManagement<Usuario> {
    private static UserServices instance;

    private UserServices() {
        super(Usuario.class);
    }

    public static UserServices getInstance(){
        if(instance==null){
            instance = new UserServices();
        }
        return instance;
    }

    public List<Usuario> findByUsername(String usuario){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u where u.id like :usuario");
        query.setParameter("usuario", usuario);
        List<Usuario> lista = query.getResultList();
        return lista;
    }
}
