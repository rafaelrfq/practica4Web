package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.FotoProducto;

public class FotoProdServices extends EntityManagement<FotoProducto>{
    private static FotoProdServices instance;

    public FotoProdServices() { super(FotoProducto.class); }

    public static FotoProdServices getInstance() {
        if(instance == null){
            instance = new FotoProdServices();
        }
        return instance;
    }
}
