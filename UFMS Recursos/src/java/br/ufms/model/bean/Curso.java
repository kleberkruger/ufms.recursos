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
@Table (name = "cursos")
public class Curso {

    @Id (generatedKey = false)
    @Column (name = "codCurso")
    private Integer codigo;
    @Column (name = "nome")
    private String nome;

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
}
