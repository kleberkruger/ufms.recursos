/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.utils.db;

import java.sql.Connection;

/**
 *
 * @author Kleber
 */
public interface InterfacePool {

    public abstract Connection getConnection();

    public void freeConnection(Connection connection);
}
