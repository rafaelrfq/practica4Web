package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.Comentario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CommentServices extends EntityManagement{
    private static CommentServices instance;

    private CommentServices() { super(Comentario.class); }

    public static CommentServices getInstance(){
        if(instance == null) {
            instance = new CommentServices();
        }
        return instance;
    }

    public List<Comentario> findByProductoId(int idProducto) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM COMENTARIO WHERE PRODUCTO_ID = :prod");
        query.setParameter("prod", idProducto);
        List<Comentario> lista = query.getResultList();
        return lista;
    }

    public List<Comentario> findById(int commentId) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM Comentario c WHERE c.id = :id");
        query.setParameter("id", commentId);
        List<Comentario> lista = query.getResultList();
        return lista;
    }
}
