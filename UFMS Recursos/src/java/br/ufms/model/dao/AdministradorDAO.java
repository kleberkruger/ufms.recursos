/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Administrador;
import br.ufms.utils.db.Pool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kleber
 */
public class AdministradorDAO implements InterfaceDAO<Administrador> {

    private Pool pool;

    public AdministradorDAO() {
        pool = Pool.getPool();
    }

    @Override
    public void inserir(Administrador bean) throws SQLException {
        String sql = "INSERT INTO administradores (nome, usuario, senha) VALUES (?, ?, ?)";

        Connection connection = pool.getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getUsuario());
            ps.setString(3, bean.getSenha());
            ps.executeUpdate();

            ps.close();

        } finally {
            pool.freeConnection(connection);
        }
    }

    @Override
    public void atualizar(Administrador bean) throws SQLException {
        String sql = "UPDATE administradores SET nome = ?, usuario = ?, senha = ? "
                + "WHERE codAdmin = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getUsuario());
            ps.setString(3, bean.getSenha());
            ps.setInt(4, bean.getCodigo());
            ps.executeUpdate();
        } finally {
            pool.freeConnection(connection);
        }
    }

    @Override
    public void excluir(Administrador bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    /**
     * Método que exclui um cliente pelo código.
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM administradores WHERE codAdmin = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM administradores";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public Administrador carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM administradores WHERE codAdmin = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        Administrador administrador = null;

        while (rs.next()) {
            administrador = new Administrador();
            administrador.setCodigo(rs.getInt("codAdmin"));
            administrador.setNome(rs.getString("nome"));
            administrador.setUsuario(rs.getString("usuario"));
            administrador.setSenha(rs.getString("senha"));
        }
        return administrador;
    }

    @Override
    public List<Administrador> listar() throws SQLException {
        String sql = "SELECT * FROM administradores ORDER BY nome";
        Connection connection = pool.getConnection();
        List<Administrador> lista = new ArrayList<Administrador>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setCodigo(rs.getInt("codAdmin"));
                administrador.setNome(rs.getString("nome"));
                administrador.setUsuario(rs.getString("usuario"));
                administrador.setSenha(rs.getString("senha"));
                lista.add(administrador);
            }
            rs.close();
            ps.close();

        } finally {
            pool.freeConnection(connection);
        }
        return lista;
    }

    @Override
    public List<Administrador> buscarPorCodigo(Integer id) throws SQLException {
        return null;
    }
}
