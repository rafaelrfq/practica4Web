package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.VentasProducto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SaleServices extends EntityManagement<VentasProducto> {
    private static SaleServices instance;

    private SaleServices(){ super(VentasProducto.class); }

    public static SaleServices getInstance(){
        if(instance == null){
            instance = new SaleServices();
        }
        return instance;
    }

    public List<VentasProducto> findByID(int id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT v FROM VentaProducto where v.id = :id");
        query.setParameter("id", id);
        List<VentasProducto> lista = query.getResultList();
        return lista;
    }
}
