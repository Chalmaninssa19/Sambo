package framework.database.utilitaire;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import framework.database.annotation.*;
public class ObjectBdd {

    //public static ArrayList<T>selectBySql()
    public static boolean isThere(ResultSet rs, String column) {
        try {
            rs.findColumn(column);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void execSQL(String sql, Connection con) throws Exception { //execute une requete sql
        System.out.println("requte : "+sql);
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static <T> T getColByExec(String sql, T type, Connection con) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rest = null;
        T retour = null;
         boolean iscon=true;
        if(con==null){
            iscon=false;
        }
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            stmt = GConnection.getSimpleConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rest = stmt.executeQuery();
            if (rest.next()) {
                retour = (T) rest.getObject(1);
            }
        } catch (Exception e) {
            throw new Exception("Requete non faites" + e.getMessage());
        }finally{
            if(!iscon){
                con.close();
            }
        }
        return retour;
    }

    public static int sequence(Object o,Connection con) throws Exception {
        String sql = "select nextval(pg_get_serial_sequence('" + ReflectClass.getTable(o) + "', 'id')) as new_id";
        return sequence_sql(sql,con);
    }

    public static int sequence(String name, Connection con) throws Exception {
        String sql = "select nextval('" + name + "') as new_id";
        return sequence_sql(sql,con);
    }

    public static int sequence_sql(String sql, Connection con) throws Exception {
        boolean iscon=true;
        if(con==null){
            iscon=false;
        }
        PreparedStatement stmt = null;
        ResultSet rest = null;
        int seq = 0;
        try {
            if(!iscon){
                con=GConnection.getSimpleConnection();
            }
            stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rest = stmt.executeQuery();
            if (rest.next()) {
                seq = rest.getInt("new_id");
            }
            return seq;
        } catch (Exception e) {
            throw new Exception("Erreur sur la sequence(" + sql + ")" + e.getMessage());
        }finally{
            if(!iscon){
                con.close();
            }
        }
    }

    private static String sqlCreate(Object ob) throws Exception { //fait la requete sql create
        Class<?> classe = ob.getClass();
        ArrayList<TreatField> attribut = ReflectClass.getColonnes(classe);
        String argument = "";
        String nomargument = "";
        Object o = null;
        for (TreatField att : attribut) {
            o = att.getValue(ob);
            if (o != null) {
                if (!argument.equals("")) {
                    argument += ",";
                }
                if (!nomargument.equals("")) {
                    nomargument += ",";
                }
                nomargument += att.getNameCol();
                if (att.getMapCol() != null) {
                    o = att.getMapCol().getValue(o);
                }

                if ((o instanceof Integer) || (o instanceof Double) || (o instanceof Float)) {
                    argument += o.toString();
                } else {
                    argument += "'" + o.toString() + "'";
                }
            }
        }
        return "INSERT INTO " + ReflectClass.getTable(ob) + "(" + nomargument + ") VALUES (" + argument + ")";
    }

    private static String sqlUpdate(Object ob) throws Exception {
        Class<?> classe = ob.getClass();
        ArrayList<TreatField> attribut = ReflectClass.getColonnes(classe);
        String change = "";
        Object o = null;
        for (TreatField att : attribut) {
            o = att.getValue(ob);
            if (o != null && !att.isPrimaryKey()) {
                if (!change.equals("")) {
                    change += ",";
                }
                change += att.getNameCol() + "=";
                if (att.getMapCol() != null) {
                    o = att.getMapCol().getValue(o);
                }
                if ((o instanceof Integer) || (o instanceof Double) || (o instanceof Float)) {
                    change += o.toString();
                } else {
                    change += "'" + o.toString() + "'";
                }
            }
        }
        Field primary = TreatField.getPrimaryKey(ob);
        primary.setAccessible(true);
        Object pk = primary.get(ob);
        if (pk instanceof String) {
            return "UPDATE " + ReflectClass.getTable(ob) + " SET " + change + " WHERE " + TreatField.getColonne(primary) + "='" + primary.get(ob) + "'";

        } else {
            return "UPDATE " + ReflectClass.getTable(ob) + " SET " + change + " WHERE " + TreatField.getColonne(primary) + "=" + primary.get(ob);
        }
    }

    private static String sqlDelete(Object ob) throws Exception {
        Field primary = TreatField.getPrimaryKey(ob);
        primary.setAccessible(true);
        Object pk = primary.get(ob);
        if (pk instanceof String) {
            return "DELETE FROM " + ReflectClass.getTable(ob) + " WHERE " + TreatField.getColonne(primary) + "='" + primary.get(ob) + "'";

        } else {
            return "DELETE FROM " + ReflectClass.getTable(ob) + " WHERE " + TreatField.getColonne(primary) + "=" + primary.get(ob);
        }
    }
    //creer l'objet
    public void create(Connection con) throws Exception { 
        boolean iscon = true;
        //System.out.println(sqlCreate());
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            System.out.println("Req : "+sqlCreate(this));
            execSQL(sqlCreate(this), con);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur sur insert(" + ReflectClass.getTable(this) + "): " + e.getMessage());
        } finally {
            if (con != null && !iscon) {
                con.close();
            }
        }
    }
    public  <T extends ObjectBdd > void createAll(ArrayList<T> liste, Connection con)throws Exception{
        boolean iscon = true;
        //System.out.println(sqlCreate());
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            con.setAutoCommit(false);
            for(T o:liste){
                o.create(con);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Erreur sur insertAll: " + e.getMessage());
        } finally {
            if (con != null && !iscon) {
                con.close();
            }
        }
    }
    public void update( Connection con, String where, String set) throws Exception { //update l'objet
        String sql = "UPDATE " + ReflectClass.getTable(this) + " SET " + set;
        if (where != null && !where.equals("")) {
            sql += " WHERE " + where;
        }
        boolean iscon = true;
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            execSQL(sql, con);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur sur update(" + ReflectClass.getTable(this) + "): " + e.getMessage());
        } finally {
            if (con != null && !iscon) {
                con.close();
            }
        }
    }

    public  void update(Connection con) throws Exception {
        boolean iscon = true;
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            execSQL(sqlUpdate(this), con);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur sur update(" + ReflectClass.getTable(this) + "): " + e.getMessage());
        } finally {
            if (con != null && !iscon) {
                con.close();
            }
        }
    }

    public void delete(Connection con) throws Exception { //delete l'objet
        String sql = sqlDelete(this);
        boolean iscon = true;
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                iscon = false;
            }
            execSQL(sql, con);
        } catch (Exception e) {
            throw new Exception("Erreur sur delete(" + ReflectClass.getTable(this) + "): " + e.getMessage());
        } finally {
            if (con != null && !iscon) {
                con.close();
            }
        }
    }

    /*
    find
     */
    private static String sqlFind(Object ob, String where) throws Exception { //fais la requete find
        String sql = "SELECT *, ROW_NUMBER() OVER() rows FROM " + ReflectClass.getTable(ob);
        if (where != null && !where.equals("")) {
            sql += " WHERE " + where;
        }
        return sql;
    }

    private static String sqlFindFk(Object ob, TreatField f, Object val)throws Exception {
        //Field p=ReflectClass.getPrimaryKey();
        String table=ReflectClass.getTable(ob);
        String sql = "SELECT * from " + table + " WHERE " + f.getMapping() + "=";
        //System.out.println(sql);
        if (val instanceof String) {
            sql += "'" + val + "'";
        } else {
            sql += val;
        }
        return sql;
    }

    private static <T> ArrayList<T> find(T obj, Connection con, String sql) throws Exception { //cherche les objets 
        boolean isCon = true;

        //System.out.println(sql);
        PreparedStatement stmt = null;
        ResultSet rest = null;
        ArrayList<T> liste = null;
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                isCon = false;
            }
            stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rest = stmt.executeQuery();
            liste = new ArrayList<T>();
            ArrayList<TreatField> attribut = ReflectClass.getColonnes(obj.getClass());
            ObjectBdd temp=null;
            while (rest.next()) {
                liste.add((T) obj.getClass().getConstructor().newInstance());

                for (TreatField att : attribut) {
                    if (att.isForeignKey() && rest.getObject(att.getNameCol())!=null ) {
                        temp=(ObjectBdd)(att.getField().getType().newInstance());
                        att.getField().setAccessible(true);
                        att.getField().set(liste.get(liste.size()-1),temp.findAOne( con, sqlFindFk(temp,att, rest.getObject(att.getNameCol()))));
                    }else{
                        att.getField().setAccessible(true);
                        att.getField().set(liste.get(liste.size() - 1), rest.getObject(att.getNameCol()));
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur sur find(" + ReflectClass.getTable(obj) + "): " + e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                rest.close();
            }
            if (con != null && !isCon) {
                con.close();
            }
        }
        return liste;
    }

    public <T> ArrayList<T> findAll(Connection con) throws Exception {
        return find((T)this, con, sqlFind((T)this, null));
    }

    public <T> ArrayList<T> findWhere(Connection con, String where) throws Exception {
        return find((T)this, con, sqlFind((T)this, where));
    }

    public <T> ArrayList<T> findBySql(Connection con, String sql) throws Exception {
        return find((T)this, con, sql);
    }

    private  <T> T findAOne( Connection con, String sql) throws Exception { //cherche un objet
        PreparedStatement stmt = null;
        ResultSet rest = null;
        T retour = null;
        int i = 0;
        boolean isCon = true;
        try {
            if (con == null) {
                con = GConnection.getSimpleConnection();
                isCon = false;
            }
            stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rest = stmt.executeQuery();
            ArrayList<TreatField> attribut = ReflectClass.getColonnes(this.getClass());
            
            ObjectBdd temp=null;
            if (rest.next()) {
                retour = (T)this.getClass().getConstructor().newInstance();

                for (TreatField att : attribut) {
                    if (att.isForeignKey()) {
                        temp=(ObjectBdd)att.getField().getType().newInstance();
                        att.getField().setAccessible(true);
                        att.getField().set(retour,temp.findAOne( con, sqlFindFk(temp,att, rest.getObject(att.getNameCol()))));
                    }else{
                        att.getField().setAccessible(true);
                        att.getField().set(retour, rest.getObject(att.getNameCol()));
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur sur findbyId" + ReflectClass.getTable(this) + "): " + e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                rest.close();
            }
            if (!isCon && con != null) {
                con.close();
            }
        }
        return retour;
    }

    public static String sqlFindOneById(Object ob, Object id) throws Exception {
        Field fieldid = TreatField.getPrimaryKey(ob);
        fieldid.setAccessible(true);
        String sql = "SELECT * FROM " + ReflectClass.getTable(ob) + " WHERE " + TreatField.getColonne(fieldid) + "=";
        if (id instanceof String) {
            sql += "'" + id + "'";
        } else {
            sql += id;
        }
        //System.out.println(sql);
        return sql;
    }

    
    
    public static String sqlFindByLimit(String basesql, Integer debut, Integer fin){
        String sql="";
        if(debut==null){
            sql+="<="+fin;
        }else if(fin==null){
            sql+=">="+debut;
        }
        else{
            sql+=" BETWEEN "+debut+fin;
        }
        return "SELECT * FROM ("+basesql+") WHERE rows "+sql ;
    }
    public <T> T findById( Connection con, Object id) throws Exception {
        return findAOne( con, sqlFindOneById(this, id));
    }

    public  <T> T findOne( Connection con) throws Exception {
        return findAOne( con, sqlFind(this, null));
    }

    public  <T> T findOneWhere( Connection con, String where) throws Exception {
        return findAOne(con, sqlFind(this, where));
    }

    public <T> T findOneBySql(Connection con, String sql) throws Exception {
        return findAOne( con, sql);
    }
    
    
    
    public <T> ArrayList<T> findAllByLimit(Connection con, Integer deb, Integer fin) throws Exception {
        return find((T)this, con, sqlFindByLimit(sqlFind((T)this, null),deb,fin));
    }

    public <T> ArrayList<T> findWhereByLimit(Connection con, String where,Integer deb, Integer fin) throws Exception {
        return find((T)this, con, sqlFindByLimit(sqlFind((T)this, where),deb,fin));
    }  

    private <T> String sqlFind(T name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
