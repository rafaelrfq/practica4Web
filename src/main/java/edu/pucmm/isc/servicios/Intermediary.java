package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.ProductoVentaProd;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Intermediary extends EntityManagement<ProductoVentaProd> {

    private static Intermediary instance;

    private Intermediary() { super(ProductoVentaProd.class); }

    public static Intermediary getInstance() {
        if(instance == null){
            instance = new Intermediary();
        }
        return instance;
    }

    public void insertarRelacion(ProductoVentaProd entidad){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("INSERT INTO PRODUCTO_VENTA_PROD (CANTIDAD, PRODUCTO_ID, VENTA_ID) VALUES (?, ?, ?)")
                .setParameter(1, entidad.getCantidad())
                .setParameter(2, entidad.getProducto().getId())
                .setParameter(3, entidad.getVenta().getId());
        try {

            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

    public List<ProductoVentaProd> findByVenta(long idVenta) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM PRODUCTO_VENTA_PROD WHERE VENTA_ID = :id", ProductoVentaProd.class);
        query.setParameter("id", idVenta);
        List<ProductoVentaProd> lista = query.getResultList();
        return lista;
    }
}