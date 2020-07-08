package edu.pucmm.isc.servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManagement {
    private static DataBaseManagement db;
    private Server tcp;

    private DataBaseManagement(){ }

    public static DataBaseManagement getInstance() {
        if(db == null){
            db = new DataBaseManagement();
        }
        return db;
    }

    public void startDB() throws SQLException {
        // Se crea el servidor
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
    }

    public void stopDB() throws SQLException {
        // Se detiene el servidor
        //Server.shutdownTcpServer("tcp://localhost:9092", "", false, false);
        tcp.stop();
    }
}