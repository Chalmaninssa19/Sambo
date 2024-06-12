/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import java.sql.Connection;
import model.Model;
import model.gestionQuai.Quai;

/**
 *
 * @author Chalman
 */
public class Disponibilite extends Model {
    @Champs(mapcol="id", name="idQuai")
    private Quai quai;
    @Champs(mapcol="id", name="idEscale")
    private Escale escale;
    
//Encapsulation

    public Quai getQuai() {
        return quai;
    }

    public void setQuai(Quai quai) {
        this.quai = quai;
    }

    public Escale getEscale() {
        return escale;
    }

    public void setEscale(Escale escale) {
        this.escale = escale;
    }
    
///Constructors
    public Disponibilite() {
    }

    public Disponibilite(Quai quai, Escale escale) {
        this.quai = quai;
        this.escale = escale;
    }
    
///Fonctions
    //Avoir l'escale d'un quai
    public Escale getEscale(Connection conn, Quai quai) throws Exception {
        return this.findOneWhere(conn, "idQuai = "+quai.getId());
    }
    
    //Avoir la disponibilite d'un quai
    public Disponibilite getDisponibilite(Connection conn, Quai quai) throws Exception {
        return this.findOneWhere(conn, "idQuai = "+quai.getId());
    }
}
