/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionBateau;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;
import model.gestionQuai.Quai;

/**
 *
 * @author Chalman
 */
public class Bateau extends Model {
    @Champs
    private String nom;
    @Champs
    private Double profondeur;
    @Champs(mapcol="id", name="idCategorieBateau")
    private CategorieBateau categorie;
    @Champs(mapcol="id", name="idPavillon")
    private Pavillon pavillon;
    @Champs
    private Double dureeRemorquage;
    @Champs
    private Double litreRavitailEauNeed;
    
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

    public CategorieBateau getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieBateau categorie) {
        this.categorie = categorie;
    }

    public Pavillon getPavillon() {
        return pavillon;
    }

    public void setPavillon(Pavillon pavillon) {
        this.pavillon = pavillon;
    }

    public Double getDureeRemorquage() {
        return dureeRemorquage;
    }
    public void setDureeRemorquage(Double dureeRemorquage) {  
        this.dureeRemorquage = dureeRemorquage;
    }

    public Double getLitreRavitailEauNeed() {
        return litreRavitailEauNeed;
    }

    public void setLitreRavitailEauNeed(Double litreRavitailEauNeed) {
        this.litreRavitailEauNeed = litreRavitailEauNeed;
    }

///Constructors
    public Bateau() {
    }

    public Bateau(String nom, Double profondeur, CategorieBateau categorie, Pavillon pavillon, Double dureeRemorquage, Double litreRavitailEauNeed) {
        this.nom = nom;
        this.profondeur = profondeur;
        this.categorie = categorie;
        this.pavillon = pavillon;
        this.dureeRemorquage = dureeRemorquage;
        this.litreRavitailEauNeed = litreRavitailEauNeed;
    }
    
///Fonctions
    //Creer un bateau dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                Bateau prom = new Bateau();
                int zoneId = this.sequence("idBateauSeq",con);
                this.setId(zoneId);
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
    
    //Recuperer toutes les bateaux
    public ArrayList<Bateau> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
    
    //Recuperer un bateau par son id
    public Bateau findById(Connection conn, Integer idBateau) throws Exception {
        return this.findOneWhere(conn, "id ="+idBateau);
    }
    
    //Recuperer tous les quais comptabiles a ce bateau
    public ArrayList<Quai> getQuaiCompatible(ArrayList<Quai> quais)  {
        ArrayList<Quai> quaisCompatible = new ArrayList<Quai>();
        for(int i = 0; i < quais.size(); i++) {
            if(quais.get(i).getProfondeur() - this.getProfondeur() > 0) {
                quaisCompatible.add(quais.get(i));
            }
        }
        
        return quaisCompatible;
    }
    
    //Recuperer le prix par littre d*un bateau
    public Double getPrixParLittre() {
        if(this.getPavillon().getId() == 1) {
            return 1.0;
        }
        return 400.0;
    }
}
