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
import model.gestionBateau.CategorieBateau;

/**
 *
 * @author Chalman
 */
public class TypeUser extends Model {
    @Champs
    private String designation;
    
///Encapsulation
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
   
///Constructors
    public TypeUser() {
    }

    public TypeUser(String designation) {
        this.designation = designation;
    }
    
///Fonctions de la classe
    //Recuperer toutes les types users
    public ArrayList<CategorieBateau> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    
    //Recuperer un type user par son id
    public CategorieBateau findById(Connection conn, Integer idCategorieBateau) throws Exception {
        return this.findOneWhere(conn, "id ="+idCategorieBateau);
    }
}
