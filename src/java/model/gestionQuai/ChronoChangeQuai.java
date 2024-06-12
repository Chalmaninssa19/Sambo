/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionQuai;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.sql.Timestamp;
import model.Model;
import model.gestionFinanciere.Escale;

/**
 *
 * @author Chalman
 */
public class ChronoChangeQuai extends Model {
    @Champs(mapcol="id",name="idEscale")
    private Escale escale;
    @Champs(mapcol="id", name="idQuai")
    private Quai quai;
    @Champs
    private Timestamp dateDebut;
    @Champs
    private Timestamp dateFin;
    
///Encapsulation
    public Escale getEscale() {
        return escale;
    }

    public void setEscale(Escale escale) {
        this.escale = escale;
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

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }
    
///Constructors
    public ChronoChangeQuai() {
    }

    public ChronoChangeQuai(Escale escale, Quai quai, Timestamp dateDebut, Timestamp dateFin) {
        this.escale = escale;
        this.quai = quai;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
///Fonctions
    //Creer une chronologie de changement de quai dans la base
    public void create (Connection con) throws Exception{
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                int zoneId = this.sequence("idChronoChangeSeq",con);
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
    
    //Avoir le dernier changement de quai dans une escale
    public ChronoChangeQuai getLastChangeQuai(Connection conn, Escale escale) throws Exception {
        String sql = "SELECT * FROM chronoChangeQuai  WHERE idEscale="+escale.getId()+" ORDER BY dateDebut DESC LIMIT 1";
        System.out.println("Requete : "+sql);
        return this.findOneBySql(conn, sql);
    }
    
    //Recuperer une chronologiede changement de quai par son id
    public ChronoChangeQuai findById(Connection conn, Integer idQuai) throws Exception {
        return this.findOneWhere(conn, "id ="+idQuai);
    }
    
    //Mettre a jour la chronologie de quai
    public void updateChronoChangeQuai(Connection conn)throws Exception {
        this.update(conn);
        conn.setAutoCommit(false);
        conn.commit();
    }
}
