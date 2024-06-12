/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.database.annotation;

import java.lang.annotation.*;

/**
 *
 * @author randr
 */
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD}) //dit quel type on peut utiliser pour cette annotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Table{
    public String nomtable() default "";
    public String sequence() default "";

    //public String nomTable();
}
