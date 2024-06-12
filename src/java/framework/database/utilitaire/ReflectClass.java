/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.database.utilitaire;

import framework.database.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author randr
 */
public class ReflectClass {
    // function that get all the fields of a class even if it is a child class

    public static Field[] getAllFields(Class<?> type) {
        Field[] fields = type.getDeclaredFields();
        if (type.getSuperclass() != null) {
            Field[] superFields = getAllFields(type.getSuperclass());
            Field[] allFields = new Field[fields.length + superFields.length];
            System.arraycopy(fields, 0, allFields, 0, fields.length);
            System.arraycopy(superFields, 0, allFields, fields.length, superFields.length);
            return allFields;
        } else {
            return fields;
        }
    }
    //function that return the name of the table
    public static <T> String getTable(T o) throws Exception {
        String table = o.getClass().getSimpleName();
        if (o.getClass().isAnnotationPresent(Table.class)) {
            Table annotation = o.getClass().getAnnotation(Table.class);
            if (!annotation.nomtable().equals("")) {
                table = annotation.nomtable();
            }
        }
        
        return table;
    }
    
    public static Field searchField(Class classe, String name)throws Exception{
        Field[] fields = getAllFields(classe);
        for(Field f:fields){
            if(f.getName().equals(name)){
                return f;
            }
        }
        return null;
    }
    //return les methode get et sette d'un field
    public static Method getMethod(Class ob, Field f) throws Exception {
        String s = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
        return searchMethod(ob,"get" + s);
    }

    public static Method setMethod(Class ob, Field f) throws Exception {
        String s = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
        return searchMethod(ob,"set" + s);
    }
    public static Method searchMethod(Class ob, String name) {
        Method[] methods = ob.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        if (ob.getSuperclass() != null) {
            return searchMethod(ob.getSuperclass(), name);
        }
        return null;
    }
    public static ArrayList<TreatField>getColonnes(Class classe)throws Exception{
        ArrayList<TreatField>liste=new ArrayList<>();
        Field[]fields=getAllFields(classe);
        Champs ann=null;
        for(Field f:fields){
            ann=f.getAnnotation(Champs.class);
            if(ann!=null){
                liste.add(new TreatField(classe,f ));
            }
        }
        return liste;
    }
}
