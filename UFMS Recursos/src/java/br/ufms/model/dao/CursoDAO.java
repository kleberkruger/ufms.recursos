/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Curso;
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
public class CursoDAO implements InterfaceDAO<Curso> {

    private Pool pool;

    public CursoDAO() {
        pool = Pool.getPool();
    }

    @Override
    public void inserir(Curso bean) throws SQLException {
        String sql = "INSERT INTO cursos (codCurso, nome) VALUES (?, ?)";

        Connection connection = pool.getConnection();
        PreparedStatement ps;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getCodigo());
            ps.setString(2, bean.getNome());
            ps.executeUpdate();
            ps.close();

        } finally {
            pool.freeConnection(connection);
        }
    }

    @Override
    public void atualizar(Curso bean) throws SQLException {
        String sql = "UPDATE cursos SET codCurso = ?, nome = ? WHERE codCurso = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getCodigo());
            ps.setString(2, bean.getNome());
            ps.setInt(3, bean.getCodigo());

            ps.executeUpdate();

        } finally {
            pool.freeConnection(connection);
        }
    }

    @Override
    public void excluir(Curso bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM cursos WHERE codCurso = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM cursos";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public Curso carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM cursos WHERE codCurso = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        Curso curso = null;

        while (rs.next()) {
            curso = new Curso();
            curso.setCodigo(rs.getInt("codCurso"));
            curso.setNome(rs.getString("nome"));
        }
        return curso;
    }

    @Override
    public List<Curso> listar() throws SQLException {
        String sql = "SELECT * FROM cursos ORDER BY nome";
        Connection connection = pool.getConnection();
        List<Curso> lista = new ArrayList<Curso>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getInt("codCurso"));
                curso.setNome(rs.getString("nome"));
                lista.add(curso);
            }
            rs.close();
            ps.close();

        } finally {
            pool.freeConnection(connection);
        }
        return lista;
    }

    @Override
    public List<Curso> buscarPorCodigo(Integer id) throws SQLException {
        return null;
    }
}
