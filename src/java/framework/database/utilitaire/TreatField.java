/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.database.utilitaire;

import java.lang.reflect.Field;

import framework.database.annotation.*;
import java.lang.reflect.Method;

public class TreatField {
    private Field field;
    private String nameCol; //nom de la colonne
    private boolean isPrimaryKey=false; 
    private boolean isForeignKey=false;
    private String mapping;
    private TreatField mapCol;//le field mapper
    private String sequenceName;
    private Method getMethod;
    private Method setMethod;
    
    public TreatField(Class c, Field f)throws Exception{
        this.field=f;
        nameCol=getColonne(f);
        isPrimaryKey=isPrimaryKey(f);
        isForeignKey=isForeignKey(f);
        mapping=mapping(f);
        mapCol=mapField(f);
        sequenceName=getSequenceName(f);
        getMethod=ReflectClass.getMethod(c,f);
        setMethod=ReflectClass.setMethod(c,f);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getNameCol() {
        return nameCol;
    }

    public void setNameCol(String nameCol) {
        this.nameCol = nameCol;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }

    public void setForeignKey(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public TreatField getMapCol() {
        return mapCol;
    }

    public void setMapCol(TreatField mapCol) {
        this.mapCol = mapCol;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }
   
    public Object getValue(Object o)throws Exception{
        return getMethod.invoke(o);
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
    
    public static boolean isColonne(Field f) { //verifie si cest une colonne
        if (f.isAnnotationPresent(Champs.class)) {;
            return true;
        }
        return false;
    }

    public static String getColonne(Field f) throws Exception { //retourne nom de la colonne
        String col = null;
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            if (!annotation.name().equals("")) {
                col = annotation.name();
            } else {
                col = f.getName();
            }
        }
        return col;
    }

    public static boolean isPrimaryKey(Field f) { //verifie si c'est une primary key
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            return annotation.primarykey();
        }
        return false;
    }

    public static Field getPrimaryKey(Object cl) { //retourne le field qui represente la primary key
        Field[] attribut = ReflectClass.getAllFields(cl.getClass());
        for (int i = 0; i < attribut.length; i++) {
            if (attribut[i].isAnnotationPresent(Champs.class)) {
                Champs annotation = attribut[i].getAnnotation(Champs.class);
                if (annotation.primarykey()) {
                    return attribut[i];
                }
            }
        }
        return null;
    }
    public static boolean isForeignKey(Field f) { //verifie si c'est une foreign key et donc une relation many to one
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            if(!annotation.mapcol().equals("")) return true;
        }
        return false;
    }
    public static String mapping(Field f) { //verifie si c'est une foreign key et donc une relation many to one
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            if(!annotation.mapcol().equals("")) return annotation.mapcol();
        }
        return null;
    }
    public static TreatField mapField(Field f)throws Exception{
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            if(!annotation.mapcol().equals("")){
                return new TreatField(f.getType(), ReflectClass.searchField(f.getType(), annotation.mapcol()));
            }
        }
        return null;
    }
    public static String getSequenceName(Field f) { //retourne le field qui represente la primary key
        if (f.isAnnotationPresent(Champs.class)) {
            Champs annotation = f.getAnnotation(Champs.class);
            return annotation.sequenceName();
        }
        return null;
    }
    
    
}
