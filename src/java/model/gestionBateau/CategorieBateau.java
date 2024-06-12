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

/**
 *
 * @author Chalman
 */
public class CategorieBateau extends Model {
    @Champs
    private String value;
    
///Encapsulation
    public String getCategorieBateau() {
        return value;
    }

    public void setCategorieBateau(String categorieBateau) {
        this.value = categorieBateau;
    }
    
///Constructors
    public CategorieBateau() {
    }
  
///Fonctions de la classe
    //Recuperer toutes les categories des bateaux
    public ArrayList<CategorieBateau> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    
    //Recuperer une categorie d'un bateau par son id
    public CategorieBateau findById(Connection conn, Integer idCategorieBateau) throws Exception {
        return this.findOneWhere(conn, "id ="+idCategorieBateau);
    }
}
