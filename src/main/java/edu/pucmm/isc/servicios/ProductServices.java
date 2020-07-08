package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductServices extends EntityManagement<Producto> {
    private static ProductServices instance;

    private ProductServices() {
        super(Producto.class);
    }

    public static ProductServices getInstance(){
        if(instance==null){
            instance = new ProductServices();
        }
        return instance;
    }

    public List<Producto> findByID(int id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p FROM Producto p where p.id = :id");
        query.setParameter("id", id);
        List<Producto> lista = query.getResultList();
        return lista;
    }
}