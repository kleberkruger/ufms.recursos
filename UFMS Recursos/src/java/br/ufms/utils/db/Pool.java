/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Kleber
 */
public class Pool implements InterfacePool {

    private static volatile Pool pool = null;
    private String url, user, password;
    private Integer maxConnections;
    private ArrayBlockingQueue<Connection> freeConnections;
    private HashMap<String, Connection> busyConnections;

    private Pool() {
        this("br.ufms.utils.db.resources.database");
    }

    private Pool(String dbInfoPath) {
        // Cria um resource bundle para ler as informações de um arquivo.
        ResourceBundle bundle = ResourceBundle.getBundle(dbInfoPath);
        // Lê as informações do arquivo.
        url = bundle.getString("url");
        user = bundle.getString("user");
        password = bundle.getString("password");
        maxConnections = Integer.parseInt(bundle.getString("maxConnections"));

        freeConnections = new ArrayBlockingQueue<Connection>(maxConnections, true);
        busyConnections = new HashMap<String, Connection>();

        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        if (busyConnections.size() < maxConnections) {
            try {
                Connection conn = freeConnections.poll();
                if (conn == null || (conn != null && conn.isClosed())) {
                    conn = DriverManager.getConnection(url, user, password);
                }
                busyConnections.put(conn.toString(), conn);
                return conn;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public void freeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
            freeConnections.add(connection);
            busyConnections.remove(connection.toString());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Método que retorna o pool de conexões. O pool gerencia as conexões com o
     * banco de dados, permitindo ou negando o acesso aos objetos do tipo
     * Connection.
     *
     * @return the pool
     */
    public static Pool getPool() {
        if (pool == null) {
            synchronized (Pool.class) {
                if (pool == null) {
                    pool = new Pool();
                }
            }
        }
        return pool;
    }
}
