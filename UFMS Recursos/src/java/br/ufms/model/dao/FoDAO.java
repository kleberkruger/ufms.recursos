/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.annotations.Column;
import br.ufms.model.annotations.Id;
import br.ufms.model.annotations.Table;
import br.ufms.utils.db.Pool;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
public abstract class FoDAO<T> implements InterfaceDAO<T> {

    private Pool pool;
    private final Class<T> cls;

    public FoDAO() {
        pool = Pool.getInstance();
        final ParameterizedType type = (ParameterizedType) getClass().
                getGenericSuperclass();
        cls = (Class<T>) type.getActualTypeArguments()[0];
    }

    public String getSQLInsert() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        String sql1 = "INSERT INTO " + tableName + " (";
        String sql2 = "";
        int count = 0;
        for (Field attr : cls.getDeclaredFields()) {
            if ((attr.getAnnotation(Id.class) == null)
                    || !(attr.getAnnotation(Id.class).generatedKey())) {

                if (count != 0) {
                    sql1 += ", ";
                    sql2 += ", ";
                }
                sql1 += attr.getAnnotation(Column.class).name();
                sql2 += '?';
                count++;
            }
        }
        sql1 += ") VALUES (" + sql2 + ")";
        return sql1;
    }

    private void populatePSInsert(PreparedStatement ps, T bean) {
        try {
            int i = 0;
            for (Field attr : cls.getDeclaredFields()) {
                if ((attr.getAnnotation(Id.class) == null)
                        || !(attr.getAnnotation(Id.class).generatedKey())) {

                    attr.setAccessible(true);
                    ps.setObject(i + 1, attr.get(bean));
                    i++;
                }
            }
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void inserir(T bean) throws SQLException {
        String sql = getSQLInsert();
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            populatePSInsert(ps, bean);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.first()) {
                try {
                    Field fld = bean.getClass().getDeclaredField("codigo");
                    fld.setAccessible(true);
                    fld.set(bean, rs.getInt(1));
                } catch (Throwable ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } finally {
            conn.close();
        }
    }

    public String getSQLUpdate() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        String fieldId = "";
        String sql = "UPDATE " + tableName + " SET ";
        int count = 0;
        for (Field attr : cls.getDeclaredFields()) {
            if (attr.getAnnotation(Column.class) != null) {
                String fieldName = attr.getAnnotation(Column.class).name();
                if (count != 0) {
                    sql += ", ";
                }
                sql += fieldName + " = ?";
                count++;
                if (attr.getAnnotation(Id.class) != null) {
                    fieldId = fieldName;
                }
            }
        }
        sql += " WHERE " + fieldId + " = ?";
        return sql;
    }

    private void populatePSUpdate(PreparedStatement ps, T bean) {
        Field attrs[] = cls.getDeclaredFields();
        try {
            for (int i = 0; i < attrs.length; i++) {
                attrs[i].setAccessible(true);
                if (attrs[i].getAnnotation(Id.class) != null) {
                    ps.setInt(i + 1, Integer.parseInt(attrs[i].get(bean) + ""));
                    ps.setInt(attrs.length + 1, Integer.parseInt(attrs[i].get(bean) + ""));
                } else {
                    ps.setObject(i + 1, attrs[i].get(bean));
                }
            }
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void atualizar(T bean) throws SQLException {
        String sql = getSQLUpdate();
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            populatePSUpdate(ps, bean);
            ps.executeUpdate();

        } finally {
            conn.close();
        }
    }

    public String getSQLDeleteByID() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        String sql = "DELETE FROM " + tableName + " WHERE ";
        for (Field attr : cls.getDeclaredFields()) {
            if (attr.getAnnotation(Id.class) != null) {
                if (attr.getAnnotation(Column.class) != null) {
                    sql += attr.getAnnotation(Column.class).name();
                } else {
                    sql += attr.getName();
                }
            }
        }
        sql += " = ?";
        return sql;
    }

    @Override
    public void excluirPorCodigo(Integer codigo) throws SQLException {
        String sql = getSQLDeleteByID();
        Connection conn = pool.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.executeUpdate();

        } finally {
            conn.close();
        }
    }

    @Override
    public void excluir(T bean) throws SQLException {
        try {
            Field attrs[] = bean.getClass().getDeclaredFields();
            int i = 0;
            while (attrs[i].getAnnotation(Id.class) == null && i < attrs.length) {
                i++;
            }
            if (i < attrs.length) {
                attrs[i].setAccessible(true);
                excluirPorCodigo((Integer) attrs[i].get(bean));
            }
        } catch (Throwable ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String getSQLDeleteAll() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        return "DELETE FROM " + tableName;
    }

    @Override
    public void excluirTodos() throws SQLException {
        String sql = getSQLDeleteAll();
        Connection conn = pool.getConnection();
        try {
            conn.createStatement().execute(sql);
        } finally {
            conn.close();
        }
    }

    public String getSQLSelectByID() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        String sql = "SELECT * FROM " + tableName + " WHERE ";
        for (Field attr : cls.getDeclaredFields()) {
            if (attr.getAnnotation(Id.class) != null) {
                if (attr.getAnnotation(Column.class) != null) {
                    sql += attr.getAnnotation(Column.class).name();
                } else {
                    sql += attr.getName();
                }
            }
        }
        sql += " = ?";
        return sql;
    }

    private T populateByRS(ResultSet rs) {
        T bean = null;
        try {
            bean = cls.newInstance();
            for (Field attr : bean.getClass().getDeclaredFields()) {
                attr.setAccessible(true);
                if (attr.getAnnotation(Column.class) != null
                        && attr.getType() == Integer.class) {

                    attr.set(bean, (attr.getType().cast(rs.getInt(attr.getAnnotation(
                            Column.class).name()))));
                } else if (attr.getAnnotation(Column.class) != null) {
                    attr.set(bean, (attr.getType().cast(rs.getObject(attr.getAnnotation(
                            Column.class).name()))));
                } else {
                    attr.set(bean, rs.getObject(attr.getName()));
                }
            }

        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
        return bean;
    }

    @Override
    public T carregar(Integer id) throws SQLException {
        String sql = getSQLSelectByID();
        Connection conn = pool.getConnection();
        T bean = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                bean = populateByRS(rs);
            }
        } finally {
            conn.close();
        }
        return bean;
    }

    public String getSQLSelectAll() {
        Table table = cls.getAnnotation(Table.class);
        String tableName = table != null ? table.name() : cls.getName();
        return "SELECT * FROM " + tableName;
    }

    @Override
    public List<T> listar() throws SQLException {
        String sql = getSQLSelectAll();
        Connection conn = pool.getConnection();
        List<T> list = new ArrayList<T>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(populateByRS(rs));
            }
        } finally {
            conn.close();
        }
        return list;
    }
}
