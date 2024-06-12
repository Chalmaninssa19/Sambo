/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.Model;
import model.profil.Profil;

/**
 *
 * @author Chalman
 */
public class ValidationPrestation extends Model {
    @Champs
    Timestamp dateValidation;
    @Champs(mapcol="id", name="idUser")
    Profil profil;
    @Champs(mapcol="id", name="idPrestation")
    Prestation prestation;
    
///Encapsulation
    public Timestamp getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Timestamp dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }
    
///Constructeurs
    public ValidationPrestation() {
    }

    public ValidationPrestation(Timestamp dateValidation, Prestation prestation) {
        this.dateValidation = dateValidation;
        this.prestation = prestation;
    }
    
///Fonctions
    //Creer un une prestation dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                int zoneId = this.sequence("idValidationPrestationSeq",con);
                this.setId(zoneId);
                super.create(con);
                con.commit();
        }catch (Exception exe) {
            con.rollback();
            throw exe;
        }finally {
            if (con!=null && !b){
                con.close();
            }
        }
    }
    
    //Recuperer toutes les prestations d'un escale
    public ArrayList<ValidationPrestation> all(Connection conn)  throws Exception { 
        return this.all(conn);
    }
    
    //Recuperer une validation par son id
    public ValidationPrestation findById(Connection conn, Integer id) throws Exception {
        return this.findOneWhere(conn, "id ="+id);
    }
}
