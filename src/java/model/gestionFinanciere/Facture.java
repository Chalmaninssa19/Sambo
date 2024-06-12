/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import model.Model;
import model.gestionBateau.Bateau;
import model.gestionBateau.Pavillon;

/**
 *
 * @author Chalman
 */
public class Facture extends Model {
    @Champs(mapcol="id", name="idEscale")
    Escale escale;
    @Champs(mapcol="id", name="idPrestation")
    Prestation prestation;
    @Champs(mapcol="id", name="idEtatValidation")
    EtatValidation etatValidation;
    
///Encapsulation
    public Escale getEscale() {
        return escale;
    }

    public void setEscale(Escale escale) {
        this.escale = escale;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }

    public EtatValidation getEtatValidation() {
        return etatValidation;
    }

    public void setEtatValidation(EtatValidation etatValidation) {
        this.etatValidation = etatValidation;
    }
    
///Constructors

    public Facture() {
    }

    public Facture(Escale escale, Prestation prestation, EtatValidation etatValidation) {
        this.escale = escale;
        this.prestation = prestation;
        this.etatValidation = etatValidation;
    }
    
///Fonctions de la classe
    //Creer un une prestation dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                int zoneId = this.sequence("idFactureSeq",con);
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
    
    //Avoir la facture d'une escale
    public ArrayList<Facture> getMyFacture(Connection conn, Escale escale)  throws Exception { 
        return this.findWhere(conn, "idEscale ="+escale.getId());
    }
    
    //Valider une facture
    public void validateFacture() throws Exception {
        
    }
    
    //Est-ce qu'une facture est valider
    public void isFactureValidate() {
        
    }
    
    //Avoir la lettre d'un etat
    public String getEtatLettre() throws Exception {
        return "";
    }
    
    //Listes de factures pour les prestations valider
    public ArrayList<Facture> getListFacturables(Connection conn, ArrayList<Prestation> prestations, Escale escale) throws Exception {
        EtatValidation etatValidation = new EtatValidation();
        ArrayList<Facture> factures = new ArrayList<>();
                System.out.println("io : "+prestations.size());
        etatValidation = etatValidation.findByValue(conn, 1);
        for(int i = 0; i < prestations.size(); i++) {
            if(prestations.get(i).isValider() == true) {
                factures.add(new Facture(escale, prestations.get(i),etatValidation));
            }
        }
        
        return factures;
    }
}
