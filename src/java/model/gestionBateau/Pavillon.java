/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionBateau;

import framework.database.annotation.Champs;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;
import model.gestionQuai.Quai;

/**
 *
 * @author Chalman
 */
public class Pavillon extends Model {
    @Champs
    private String nom;
    
///Encapsulation
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
///Fonctions
     //Recuperer toutes les quais
    public ArrayList<Pavillon> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
    
    //Recuperer un quai par son id
    public Pavillon findById(Connection conn, Integer idPavillon) throws Exception {
        return this.findOneWhere(conn, "id ="+idPavillon);
    }
}
