/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionBateau;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import model.Model;

/**
 *
 * @author Chalman
 */
public class Prevision extends Model{
    @Champs
    Timestamp dateDebut;
    @Champs
    Timestamp dateFin;
    @Champs(mapcol="id", name="idBateau")
    Bateau bateau;
    @Champs
    Double dureeEscale;
    
///Encapsulation

    
    
    public Timestamp getDateDebut( ) {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Bateau getBateau() {
        return bateau;
    }

    public void setBateau(Bateau bateau) {
        this.bateau = bateau;
    }

    public Double getDureeEscale() {
        return dureeEscale;
    }

    public void setDureeEscale() {
        // Calcul de la différence en minutes
        long differenceInMillis = getDateFin().getTime() - getDateDebut().getTime();
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        String duree = String.valueOf(differenceInMinutes);
        this.dureeEscale = Double.valueOf(differenceInMinutes);
    }

    public void setDate(Timestamp dateFin, Timestamp dateDebut) throws Exception {
        // Obtenir la date et l'heure actuelles
        Date currentDate = new Date();
        
        // Convertir la date en timestamp
        Timestamp dateNow = new Timestamp(currentDate.getTime());

        // Obtenir une instance de Calendar à partir du timestamp
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFin);
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTime(dateDebut);
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(dateNow);

        // Extraire l'heure de la date
        int yearArrive = calendar.get(Calendar.YEAR);
        int yearDepart = calendar1.get(Calendar.YEAR);

        if(yearArrive > 2025 && yearDepart > 2025) {
            throw new Exception("Entrer des dates proches");
        }
        if(dateFin.getTime() < dateDebut.getTime()) {
            throw new Exception("Date arrive doit etre superieur a date de depart");
        }
        if(dateNow.getTime() > dateDebut.getTime()) {
            throw new Exception("Date non disponible");
        }
        this.setDateDebut(dateDebut);
        this.setDateFin(dateFin);
    }

    ///Constructors
    public Prevision() {
    }
    public Prevision(Timestamp dateDebut, Timestamp dateFin, Bateau bateau) throws Exception {
        try {
        this.setDate(dateFin, dateDebut);
        this.bateau = bateau;
        this.setDureeEscale();
        } catch(Exception e) {
            throw e;
        }
    }
    
///Fonctions
    //Creer une prevision dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
            if (con==null){
                con = GConnection.getSimpleConnection();
                b = false ;
            }
            con.setAutoCommit(false);    
            Prevision prevision = new Prevision();
            int prevId = this.sequence("idPrevisionSeq",con);
            this.setId(prevId);
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
    //Recuperer toutes les previsions
    public ArrayList<Prevision> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    //Recuperer une prevision par son id
    public Prevision findById(Connection conn, Integer idPrevision) throws Exception {
        return this.findOneWhere(conn, "idPrevision ="+idPrevision);
    }
    //Recuperer une prevision d'un bateau
    public Prevision findPrevisionBateau(Connection conn, Bateau bateau) throws Exception {
        return this.findOneWhere(conn, "idBateau ="+bateau.getId());
    }
    //Trier previsions par date
    public ArrayList<Prevision> listPrevisionTrierByDateArrive(Connection conn) throws Exception {
        String sql = "SELECT * FROM prevision ORDER BY dateDebut asc";
        return this.findBySql(conn, sql);
    }
}
