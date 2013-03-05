/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Professor;
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
public class ProfessorDAO implements InterfaceDAO<Professor> {

    private Pool pool;

    public ProfessorDAO() {
        pool = Pool.getInstance();
    }

    @Override
    public void inserir(Professor bean) throws SQLException {
        String sql = "INSERT INTO professores (nome, login, senha, siape, "
                + "telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = pool.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getLogin());
            ps.setString(3, bean.getSenha());
            ps.setString(4, bean.getSiape());
            ps.setString(5, bean.getTelefone());
            ps.setString(6, bean.getEmail());
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
    public void atualizar(Professor bean) throws SQLException {
        String sql = "UPDATE professores SET nome = ?, login = ?, senha = ?, "
                + "siape = ?, telefone = ?, email = ? WHERE codProf = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getNome());
            ps.setString(2, bean.getLogin());
            ps.setString(3, bean.getSenha());
            ps.setString(4, bean.getSiape());
            ps.setString(5, bean.getTelefone());
            ps.setString(6, bean.getEmail());
            ps.setInt(7, bean.getCodigo());
            ps.executeUpdate();

        } finally {
            connection.close();
        }
    }

    @Override
    public void excluir(Professor bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM professores WHERE codProf = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM professores";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public Professor carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM professores WHERE codProf = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        Professor prof = null;

        while (rs.next()) {
            prof = new Professor();
            prof.setCodigo(rs.getInt("codProf"));
            prof.setNome(rs.getString("nome"));
            prof.setLogin(rs.getString("login"));
            prof.setSenha(rs.getString("senha"));
            prof.setSiape(rs.getString("siape"));
            prof.setTelefone(rs.getString("telefone"));
            prof.setEmail(rs.getString("email"));
        }
        return prof;
    }

    @Override
    public List<Professor> listar() throws SQLException {
        String sql = "SELECT * FROM professores ORDER BY nome";
        Connection connection = pool.getConnection();
        List<Professor> lista = new ArrayList<Professor>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Professor prof = new Professor();
                prof.setCodigo(rs.getInt("codProf"));
                prof.setNome(rs.getString("nome"));
                prof.setLogin(rs.getString("login"));
                prof.setSenha(rs.getString("senha"));
                prof.setSiape(rs.getString("siape"));
                prof.setTelefone(rs.getString("telefone"));
                prof.setEmail(rs.getString("email"));
                lista.add(prof);
            }
            rs.close();
            ps.close();

        } finally {
            connection.close();
        }
        return lista;
    }
}