/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.bean;

import br.ufms.model.annotations.Column;
import br.ufms.model.annotations.Id;
import br.ufms.model.annotations.Table;

/**
 *
 * @author Kleber
 */
@Table(name = "administradores")
public class Administrador {

    @Id
    @Column(name = "codAdmin")
    private Integer codigo;
    @Column(name = "nome")
    private String nome;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "senha")
    private String senha;

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
