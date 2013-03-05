/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.TipoEquipamento;
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
public class TipoEquipamentoDAO implements InterfaceDAO<TipoEquipamento> {

    private Pool pool;

    public TipoEquipamentoDAO() {
        pool = Pool.getInstance();
    }

    @Override
    public void inserir(TipoEquipamento bean) throws SQLException {
        String sql = "INSERT INTO tipos_equipamentos (descricao) VALUES (?)";
        Connection connection = pool.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getDescricao());
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
    public void atualizar(TipoEquipamento bean) throws SQLException {
        String sql = "UPDATE tipos_equipamentos SET descricao = ? WHERE codTipo = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getDescricao());
            ps.setInt(2, bean.getCodigo());

            ps.executeUpdate();

        } finally {
            connection.close();
        }
    }

    @Override
    public void excluir(TipoEquipamento bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM tipos_equipamentos WHERE codTipo = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM tipos_equipamentos";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public TipoEquipamento carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM tipos_equipamentos WHERE codTipo = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        TipoEquipamento tipo = null;

        while (rs.next()) {
            tipo = new TipoEquipamento();
            tipo.setCodigo(rs.getInt("codTipo"));
            tipo.setDescricao(rs.getString("descricao"));
        }
        return tipo;
    }

    @Override
    public List<TipoEquipamento> listar() throws SQLException {
        String sql = "SELECT * FROM tipos_equipamentos ORDER BY descricao";
        Connection connection = pool.getConnection();
        List<TipoEquipamento> lista = new ArrayList<TipoEquipamento>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipoEquipamento tipo = new TipoEquipamento();
                tipo.setCodigo(rs.getInt("codTipo"));
                tipo.setDescricao(rs.getString("descricao"));
                lista.add(tipo);
            }
            rs.close();
            ps.close();

        } finally {
            connection.close();
        }
        return lista;
    }
}
