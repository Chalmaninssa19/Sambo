/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import model.Model;
import model.gestionBateau.Bateau;
import model.gestionQuai.Quai;

/**
 *
 * @author Chalman
 */
public class Escale extends Model{
    @Champs
    String reference;
    @Champs(mapcol="id", name="idBateau")
    private Bateau bateau;
    @Champs(mapcol="id", name="idQuai")
    private Quai quai;
    @Champs
    private Timestamp dateDebut;
    @Champs
    private Timestamp dateFin;
    private Double duree;
    
///Encapsulation
    public Bateau getBateau() {
        return bateau;
    }

    public void setBateau(Bateau bateau) {
        this.bateau = bateau;
    }

    public Quai getQuai() {
        return quai;
    }

    public void setQuai(Quai quai) {
        this.quai = quai;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) throws Exception {
        this.dateFin = dateFin; 
    }

    public String getReference() {
        return reference;
    }

    public void setReference() {
        // Conversion du timestamp en objet Date
        Date date = new Date(this.getDateDebut().getTime());

        // Création d'une instance de Calendar et configuration de la date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extraction de l'heure et du mois
        int mois = calendar.get(Calendar.MONTH) + 1; // Les mois sont indexés à partir de zéro, donc on ajoute 1
        int year = calendar.get(Calendar.YEAR);
         int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String ref = "Esc"+year+""+mois+""+day+""+this.getBateau().getId();
        this.reference = ref;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree() throws Exception {
        // Calcul de la différence en minutes
        long differenceInMillis = getDateFin().getTime() - getDateDebut().getTime();
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        Double duree = Double.valueOf(differenceInMinutes);
        System.out.println("duree : "+duree);
        if(duree > 0) {
                  System.out.println("duree ");
            this.duree = duree;
        }
        else {
                  System.out.println("exeption");
            throw new Exception("Date fin doit etre superieur a "+this.getDateDebut());
        }
    }

    
///Constructors
    public Escale() {
    }

    public Escale(Bateau bateau, Quai quai, Timestamp dateDebut, Timestamp dateFin) throws Exception {
        try {
            this.setBateau(bateau);
            this.setQuai(quai);
            this.setDateDebut(dateDebut);
            this.setDateFin(dateFin);
            this.setReference(); 
        } catch(Exception e) {
            throw e;
        }
    }  
    
    public Escale(Bateau bateau, Timestamp dateDebut, Timestamp dateFin) throws Exception {
        try {
           this.setBateau(bateau);
            this.setDateDebut(dateDebut);
            this.setDateFin(dateFin);
            this.setReference();  
        } catch(Exception e) {
            throw e;
        }
    }
   
///Fonctions
    //Creer un escale dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                Escale escale = new Escale();
                int zoneId = this.sequence("idEscaleSeq",con);
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
    
    //Recuperer toutes les escales
    public ArrayList<Escale> all(Connection conn)  throws Exception { 
        return this.findAll(conn);
    }
    
    //Recuperer toutes les escales trie par id
    public ArrayList<Escale> allTrieByIdDesc(Connection conn)  throws Exception { 
        String sql = "SELECT * FROM escale ORDER BY id DESC";
        return this.findBySql(conn, sql);
    }
    
    //Recuperer un escale par son id
    public Escale findById(Connection conn, Integer idEscale) throws Exception {
        return this.findOneWhere(conn, "id ="+idEscale);
    }
    
    public String getEtatLettre() throws Exception {
        if(this.getDateFin() != null) {
            return "Termine";
        }
        return "En cours";
    }
    
    public boolean isEnd() throws Exception {
        if(this.getDateFin() != null) {
            return true;
        }
        return false;
    }
    
    //Avoir toutes les prestations qui ont lieu dans l'escale
    public ArrayList<Prestation> getPrestations(Connection conn) throws Exception {
        Prestation prestation = new Prestation();
        return prestation.getMyPrestations(conn, this);
    }
    
    //Avoir la facture du prestation
    public ArrayList<Facture> getFacture(Connection conn, ArrayList<Prestation> prestations) throws Exception {
        Facture facture = new Facture();
        return facture.getListFacturables(conn, prestations, this);
    }
    
    //Somme a payer dans une facture d'un escale
    public Double getMontant(ArrayList<Prestation> prestations) throws Exception {
        return null;
    }
    
    //Valider facture
    public void validerFacture(Connection conn, Escale escale, ArrayList<Facture> factures) throws Exception {
        EtatValidation etatValidation = new EtatValidation();
        etatValidation = etatValidation.findByValue(conn, 11);
        for(int i = 0; i < factures.size(); i++) {
            Facture facture = new Facture(escale, factures.get(i).getPrestation(), etatValidation);
            facture.create(conn);
        }
    }
    
    //Est ce que l'escale est facture
    public boolean isFacturer(Connection conn) throws Exception {
        Facture facture = new Facture();
        ArrayList<Facture> factures =  facture.getMyFacture(conn, this);
        System.out.println("taille : "+factures.size());
        if(factures.size() > 0) {
            return true;
        }
        return false;
    }
    
    //Mise a jour de l'escale
    public void updateEscale(Connection conn) throws Exception {
        this.update(conn);
        conn.setAutoCommit(false);
        conn.commit();
    }
    
    //Le Montant total de la facture de l'escale
    public Double getMontantTotal(ArrayList<Facture> listFacture) throws Exception {
        Double sum = 0.0;
        for(int i = 0; i < listFacture.size(); i++) {
            sum += listFacture.get(i).getPrestation().getMontant();
        }
        
        return sum;
    } 
}
