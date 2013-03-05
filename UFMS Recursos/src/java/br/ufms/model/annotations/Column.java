/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufms.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Kleber
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Column {

    public String name() default "";

    public String table() default "";
}
