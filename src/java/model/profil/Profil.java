/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.profil;

import framework.database.annotation.Champs;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;

/**
 *
 * @author Chalman
 */
public class Profil extends Model {
    @Champs
    String designation;
    
///Encapsulation
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    
///Constructors
    public Profil() {
    }

///Fonctions de la classe
     //Recuperer toutes les users
    public ArrayList<Profil> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
    
    //Recuperer un user par son id
    public Profil findById(Connection conn, Integer idTypeUser) throws Exception {
        return this.findOneWhere(conn, "id ="+idTypeUser);
    }
}
