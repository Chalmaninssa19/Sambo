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

/**
 *
 * @author Chalman
 */
public class EtatValidation extends Model {
    @Champs
    private Integer value;
///Encapsulation
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    
///Constructors
    public EtatValidation() {}

    public EtatValidation(Integer value) {
        this.value = value;
    }
  
///Fonctions
    //Recuperer toutes les etats validations
    public ArrayList<EtatValidation> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
        
    //Recuperer une validation par son id
    public EtatValidation findById(Connection conn, Integer idEtat) throws Exception {
        return this.findOneWhere(conn, "id ="+idEtat);
    }   
         
    //Recuperer une validation par son valeur
    public EtatValidation findByValue(Connection conn, Integer valeur) throws Exception {
        return this.findOneWhere(conn, "value ="+valeur);
    }
    
    //Recuperer les etats lettres
    public String getEtatLettre() {
        if(this.getValue() <= 1) {
            return "creer";
        }
        if(this.getValue() <= 11 && this.getValue() >1) {
            return "Valider";
        }
        if(this.getValue() > 11) {
            return "Payer";
        }
        return null;
    }
}
