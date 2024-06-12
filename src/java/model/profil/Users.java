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
public class Users extends Model {
    @Champs
    private String nom;
    @Champs(mapcol="id", name="idTypeUser")
    private TypeUser typeUser;
    
///Encapsulation
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }
   
///Constructor
    public Users() {
    }

    public Users(String nom, TypeUser typeUser) {
        this.nom = nom;
        this.typeUser = typeUser;
    }
  
///Fonction
    //Recuperer toutes les users
    public ArrayList<Users> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    
    //Recuperer un user par son id
    public Users findById(Connection conn, Integer idUser) throws Exception {
        return this.findOneWhere(conn, "id ="+idUser);
    }
}
