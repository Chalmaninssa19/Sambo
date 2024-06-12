/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;
import model.gestionBateau.CategorieBateau;

/**
 *
 * @author Chalman
 */
public class TypePrestation extends Model {
    @Champs
    String designation;
    
///Encapsulation
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
///Fonctions
    //Recuperer toutes les types de prestation
    public ArrayList<TypePrestation> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    
    //Recuperer une type de prestatio par son id
    public TypePrestation findById(Connection conn, Integer idTypePrestation) throws Exception {
        return this.findOneWhere(conn, "id ="+idTypePrestation);
    }
}
