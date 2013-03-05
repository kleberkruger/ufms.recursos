/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Disciplina;
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
public class DisciplinaDAO implements InterfaceDAO<Disciplina> {

    private Pool pool;

    public DisciplinaDAO() {
        pool = Pool.getInstance();
    }

    @Override
    public void inserir(Disciplina bean) throws SQLException {
        String sql = "INSERT INTO disciplinas (nome) VALUES (?)";
        Connection connection = pool.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getNome());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.first()) {
                bean.setCodigo(rs.getInt(1));
            }
            ps.close();
            rs.close();
            
        } finally {
            connection.close();
        }
    }

    @Override
    public void atualizar(Disciplina bean) throws SQLException {
        String sql = "UPDATE disciplinas SET nome = ? WHERE codDisc = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getNome());
            ps.setInt(2, bean.getCodigo());

            ps.executeUpdate();

        } finally {
            connection.close();
        }
    }

    @Override
    public void excluir(Disciplina bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM disciplinas WHERE codDisc = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM disciplinas";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public Disciplina carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM disciplinas WHERE codDisc = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        Disciplina disciplina = null;

        while (rs.next()) {
            disciplina = new Disciplina();
            disciplina.setCodigo(rs.getInt("codDisc"));
            disciplina.setNome(rs.getString("nome"));
        }
        return disciplina;
    }

    @Override
    public List<Disciplina> listar() throws SQLException {
        String sql = "SELECT * FROM disciplinas ORDER BY nome";
        Connection connection = pool.getConnection();
        List<Disciplina> lista = new ArrayList<Disciplina>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Disciplina curso = new Disciplina();
                curso.setCodigo(rs.getInt("codDisc"));
                curso.setNome(rs.getString("nome"));
                lista.add(curso);
            }
            rs.close();
            ps.close();

        } finally {
            connection.close();
        }
        return lista;
    }

    
}
