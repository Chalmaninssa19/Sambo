/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.database.annotation;;;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author randr
 */
@Target({ElementType.FIELD}) //dit quel type on peut utiliser pour cette annotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Champs {
    public String name() default "";
    public boolean primarykey() default false;
    public String mapcol() default "";   
    public String sequenceName() default "";
}
