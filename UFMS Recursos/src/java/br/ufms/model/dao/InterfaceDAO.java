/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

/**
 * Interface abstrata para implementação de uma Classe DAO.
 *
 * @author Kleber
 */
import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<Bean> {

    public void inserir(Bean bean) throws SQLException;

    public void atualizar(Bean bean) throws SQLException;

    public void excluir(Bean bean) throws SQLException;

    public void excluirPorCodigo(Integer id) throws SQLException;

    public void excluirTodos() throws SQLException;

    public Bean carregar(Integer id) throws SQLException;

    public List<Bean> listar() throws SQLException;
    
    public List<Bean> buscarPorCodigo(Integer id) throws SQLException;
}