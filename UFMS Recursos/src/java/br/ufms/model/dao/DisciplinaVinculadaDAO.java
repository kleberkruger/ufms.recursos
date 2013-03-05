/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.DisciplinaVinculada;
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
public class DisciplinaVinculadaDAO {

    private Pool pool;

    public DisciplinaVinculadaDAO(Pool pool) {
        this.pool = pool;
    }

    public void inserir(DisciplinaVinculada bean) throws SQLException {
        String sql = "INSERT INTO disciplinas_vinculadas (codCurso, codDisc, "
                + "codProf) VALUES (?, ?, ?)";
        Connection connection = pool.getConnection();
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, bean.getCurso().getCodigo());
            ps.setInt(2, bean.getDisciplina().getCodigo());
            ps.setInt(3, bean.getProfessor().getCodigo());
            
            ps.executeUpdate();
            ps.close();

        } finally {
            connection.close();
        }
    }

    public void atualizar(DisciplinaVinculada bean) throws SQLException {
        String sql = "UPDATE disciplinas_vinculadas SET codCurso = ?, "
                + "codDisc = ?, codProf = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getCurso().getCodigo());
            ps.setInt(2, bean.getDisciplina().getCodigo());
            ps.setInt(3, bean.getProfessor().getCodigo());
            ps.executeUpdate();
        } finally {
            connection.close();
        }
    }

    public void excluir(DisciplinaVinculada bean) throws SQLException {
        String sql = "DELETE FROM disciplinas_vinculadas WHERE codCurso = ? "
                + "and codDisc = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, bean.getCurso().getCodigo());
        ps.setInt(2, bean.getDisciplina().getCodigo());
        ps.executeUpdate();
    }

    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM disciplinas_vinculadas";
        pool.getConnection().createStatement().execute(sql);
    }

    public DisciplinaVinculada carregar(Integer codCurso, Integer codDisc) throws SQLException {
        String sql = "SELECT * FROM disciplinas_vinculadas WHERE codCurso = ? and codDisc = ?";
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        DisciplinaVinculada dv = null;
        CursoDAO cursoDAO = new CursoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        while (rs.next()) {
            dv = new DisciplinaVinculada();
            dv.setCurso(cursoDAO.carregar(rs.getInt("codCurso")));
            dv.setDisciplina(disciplinaDAO.carregar(rs.getInt("codDisc")));
            dv.setProfessor(professorDAO.carregar(rs.getInt("codProf")));
        }
        return dv;
    }

    public List<DisciplinaVinculada> listar() throws SQLException {
        String sql = "SELECT * FROM disciplinas_vinculadas";
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        List<DisciplinaVinculada> lista = new ArrayList<DisciplinaVinculada>();
        CursoDAO cursoDAO = new CursoDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        while (rs.next()) {
            DisciplinaVinculada dv = new DisciplinaVinculada();
            dv.setCurso(cursoDAO.carregar(rs.getInt("codCurso")));
            dv.setDisciplina(disciplinaDAO.carregar(rs.getInt("codDisc")));
            dv.setProfessor(professorDAO.carregar(rs.getInt("codProf")));
            lista.add(dv);
        }
        return lista;
    }

    public List<DisciplinaVinculada> buscarPorCodigo(Integer id) throws SQLException {
        return null;
    }
}