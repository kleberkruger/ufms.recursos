/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.dao;

import br.ufms.model.bean.Administrador;
import br.ufms.model.bean.Curso;

/**
 *
 * @author Kleber
 */
public class Main<T> {

    public static void main(String[] args) {
        
        FoDAO<Administrador> adminDao = new FoDAO<Administrador>() {
        };

        Administrador admin = new Administrador();
        admin.setCodigo(3);
        admin.setNome("Kleber Kruger");
        admin.setUsuario("kleberkruger");
        admin.setSenha("******");

        System.out.println(adminDao.getSQLInsert());
        System.out.println(adminDao.getSQLUpdate());
        System.out.println(adminDao.getSQLDeleteAll());
        System.out.println(adminDao.getSQLDeleteByID());
        System.out.println(adminDao.getSQLSelectAll());
        System.out.println(adminDao.getSQLSelectByID());
        System.out.println("");

        FoDAO<Curso> cursoDao = new FoDAO<Curso>() {
        };
        
        System.out.println(cursoDao.getSQLInsert());
        System.out.println(cursoDao.getSQLUpdate());
        System.out.println(cursoDao.getSQLDeleteAll());
        System.out.println(cursoDao.getSQLDeleteByID());
        System.out.println(cursoDao.getSQLSelectAll());
        System.out.println(cursoDao.getSQLSelectByID());
    }
}
