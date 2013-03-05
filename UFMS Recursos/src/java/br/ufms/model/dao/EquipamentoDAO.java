/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Equipamento;
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
public class EquipamentoDAO implements InterfaceDAO<Equipamento> {

    private Pool pool;

    public EquipamentoDAO() {
        pool = Pool.getInstance();
    }

    @Override
    public void inserir(Equipamento bean) throws SQLException {
        String sql = "INSERT INTO equipamentos (codTipo, descricao) VALUES (?, ?)";
        Connection connection = pool.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, bean.getTipo().getCodigo());
            ps.setString(2, bean.getDescricao());
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
    public void atualizar(Equipamento bean) throws SQLException {
        String sql = "UPDATE equipamentos SET codTipo = ?, descricao = ? WHERE codEquip = ?";
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, bean.getTipo().getCodigo());
            ps.setString(2, bean.getDescricao());
            ps.setInt(3, bean.getCodigo());

            ps.executeUpdate();

        } finally {
            connection.close();
        }
    }

    @Override
    public void excluir(Equipamento bean) throws SQLException {
        excluirPorCodigo(bean.getCodigo());
    }

    @Override
    public void excluirPorCodigo(Integer id) throws SQLException {
        String sql = "DELETE FROM equipamentos WHERE codEquip = ?";
        PreparedStatement ps = pool.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = "DELETE FROM equipamentos";
        pool.getConnection().createStatement().execute(sql);
    }

    @Override
    public Equipamento carregar(Integer id) throws SQLException {
        String sql = "SELECT * FROM equipamentos WHERE codEquip = " + id;
        ResultSet rs = pool.getConnection().prepareStatement(sql).executeQuery();
        Equipamento tipo = null;
        TipoEquipamentoDAO dao = new TipoEquipamentoDAO();

        while (rs.next()) {
            tipo = new Equipamento();
            tipo.setCodigo(rs.getInt("codEquip"));
            tipo.setTipo(dao.carregar(rs.getInt("codTipo")));
            tipo.setDescricao(rs.getString("descricao"));
        }
        return tipo;
    }

    @Override
    public List<Equipamento> listar() throws SQLException {
        String sql = "SELECT * FROM equipamentos ORDER BY descricao";
        Connection connection = pool.getConnection();
        List<Equipamento> lista = new ArrayList<Equipamento>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            TipoEquipamentoDAO dao = new TipoEquipamentoDAO();

            while (rs.next()) {
                Equipamento equipamento = new Equipamento();
                equipamento.setCodigo(rs.getInt("codEquip"));
                equipamento.setTipo(dao.carregar(rs.getInt("codTipo")));
                equipamento.setDescricao(rs.getString("descricao"));
                lista.add(equipamento);
            }
            rs.close();
            ps.close();

        } finally {
            connection.close();
        }
        return lista;
    }

    
}
