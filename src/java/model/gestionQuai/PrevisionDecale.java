/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionQuai;

import java.sql.Timestamp;
import model.gestionBateau.Prevision;

/**
 *
 * @author Chalman
 */
public class PrevisionDecale {
    Prevision previsionDecale;
    Timestamp dateFin;
    
///Encapsulation

    public Prevision getPrevisionDecale() {
        return previsionDecale;
    }

    public void setPrevisionDecale(Prevision previsionDecale) {
        this.previsionDecale = previsionDecale;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }


///Constructors
    
    public PrevisionDecale() {
    }

    public PrevisionDecale(Prevision previsionDecale, Timestamp dateFin) {
        this.previsionDecale = previsionDecale;
        this.dateFin = dateFin;
    }
}
