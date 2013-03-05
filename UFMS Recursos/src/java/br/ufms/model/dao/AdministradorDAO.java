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
        pool = Pool.getInstance();
    }

    @Override
    public void inserir(Administrador bean) throws SQLException {
        String sql = "INSERT INTO administradores (nome, usuario, senha) "
                + "VALUES (?, ?, ?)";
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getUsuario());
            ps.setString(3, bean.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.first()) {
                bean.setCodigo(rs.getInt(1));
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public void atualizar(Administrador bean) throws SQLException {
        String sql = "UPDATE administradores SET nome = ?, usuario = ?, "
                + "senha = ? WHERE codAdmin = ?";
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getUsuario());
            ps.setString(3, bean.getSenha());
            ps.setInt(4, bean.getCodigo());
            ps.executeUpdate();

        } finally {
            conn.close();
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
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } finally {
            conn.close();
        }
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM administradores";
        Connection conn = pool.getConnection();
        try {
            conn.createStatement().execute(sql);
        } finally {
            conn.close();
        }
    }

    @Override
    public Administrador carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM administradores WHERE codAdmin = ?";
        Connection conn = pool.getConnection();
        Administrador administrador = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                administrador = new Administrador();
                administrador.setCodigo(rs.getInt("codAdmin"));
                administrador.setNome(rs.getString("nome"));
                administrador.setUsuario(rs.getString("usuario"));
                administrador.setSenha(rs.getString("senha"));
            }
        } finally {
            conn.close();
        }
        return administrador;
    }

    public Administrador carregar(String usuario) throws SQLException {
        String sql = "SELECT * FROM administradores WHERE usuario = ?";
        Connection conn = pool.getConnection();
        Administrador administrador = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                administrador = new Administrador();
                administrador.setCodigo(rs.getInt("codAdmin"));
                administrador.setNome(rs.getString("nome"));
                administrador.setUsuario(rs.getString("usuario"));
                administrador.setSenha(rs.getString("senha"));
            }
        } finally {
            conn.close();
        }
        return administrador;
    }

    @Override
    public List<Administrador> listar() throws SQLException {
        String sql = "SELECT * FROM administradores ORDER BY nome";
        Connection conn = pool.getConnection();
        List<Administrador> lista = new ArrayList<Administrador>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setCodigo(rs.getInt("codAdmin"));
                administrador.setNome(rs.getString("nome"));
                administrador.setUsuario(rs.getString("usuario"));
                administrador.setSenha(rs.getString("senha"));
                lista.add(administrador);
            }
        } finally {
            conn.close();
        }
        return lista;
    }

    
}