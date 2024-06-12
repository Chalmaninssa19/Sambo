/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionQuai;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;
import model.gestionBateau.Prevision;

/**
 *
 * @author Chalman
 */
public class Quai extends Model {
    @Champs
    private String nom;
    @Champs
    private Double profondeur;
    @Champs
    private boolean isDispo; 
    private Double montant;
    
///Encapsulation
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getProfondeur() {
        return profondeur;
    }
    public void setProfondeur(Double profondeur) {
        this.profondeur = profondeur;
    }

    public boolean getIsDispo() {
        return isDispo;
    }

    public void setIsDispo(boolean isLibre) {
        this.isDispo = isLibre;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
    
///Constructors

    public Quai() {
    }

    public Quai(String nom, Double profondeur, boolean isDispo) {
        this.nom = nom;
        this.profondeur = profondeur;
        this.isDispo = isDispo;
    }
    
///Fonctions
    //Creer un quai dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                Quai quai = new Quai();
                int quaiId = this.sequence("idQuaiSeq",con);
                this.setId(quaiId);
                super.create(con);
                con.commit();
        }catch (Exception exe) {
            //System.out.println(exe.getMessage());
            con.rollback();
            throw exe;
        }finally {
            if (con!=null && !b){
                con.close();
            }
        }
    }
    
    //Recuperer toutes les quais
    public ArrayList<Quai> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
    
    //Recuperer un quai par son id
    public Quai findById(Connection conn, Integer idQuai) throws Exception {
        return this.findOneWhere(conn, "id ="+idQuai);
    }
    
    //Recuperer les quais disponibles
    public ArrayList<Quai> findQuaiDispo(Connection conn) throws Exception {
        return this.findWhere(conn, "isDispo=true");
    }
    
    //Recuperer les quais indisponibles
    public ArrayList<Quai> findQuaiIndisDispo(Connection conn) throws Exception {
        return this.findWhere(conn, "isDispo=false");
    }
    
    //Mettre a jour la table quai
    public void updateQuai(Connection conn)throws Exception {
        this.update(conn);
        conn.commit();
    }
    
    //Trier previsions par date
    public ArrayList<Quai> listQuaiTrierByProfondeur(Connection conn) throws Exception {
        String sql = "SELECT * FROM quai ORDER BY profondeur asc";
        return this.findBySql(conn, sql);
    }
}
