/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.utils.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Kleber
 */
public class Pool {

    private DataSource dataSource;

    private Pool() {
        try {
            Context context = new InitialContext();
            context = (Context) context.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/equipamentos");
        } catch (NamingException ex) {
            Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static Pool getInstance() {
        return PoolHolder.INSTANCE;
    }

    private static class PoolHolder {

        private static final Pool INSTANCE = new Pool();
    }
}
