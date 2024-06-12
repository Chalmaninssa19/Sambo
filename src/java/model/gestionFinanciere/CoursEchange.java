/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import model.Model;

/**
 *
 * @author Chalman
 */
public class CoursEchange extends Model {
    @Champs
    private Double ariary;
    @Champs
    private Double euro;
    @Champs
    private Date dateEchange;
    
///Encapsulation
    public Double getAriary() {
        return ariary;
    }

    public void setAriary(Double ariary) {
        this.ariary = ariary;
    }

    public Double getEuro() {
        return euro;
    }

    public void setEuro(Double euro) {
        this.euro = euro;
    }

    public Date getDateEchange() {
        return dateEchange;
    }

    public void setDateEchange(Date dateEchange) {
        this.dateEchange = dateEchange;
    }
    
///Constructors
    public CoursEchange() {
    }

    public CoursEchange(Double ariary, Double euro, Date dateEchange) {
        this.ariary = ariary;
        this.euro = euro;
        this.dateEchange = dateEchange;
    }
    
///Fonctions
    //Avoir le cours d'echange la plus proche de l'escale
    public CoursEchange getEchangeProcheEscale(Connection conn, Escale escale) throws Exception {
        String sql = "";
        if(escale.getDateFin() != null) {
            sql = "SELECT * FROM coursEchange WHERE dateechange <= '"+escale.getDateFin()+"' ORDER BY dateechange DESC LIMIT 1";
        }
        else {
            sql = "SELECT * FROM coursEchange WHERE dateechange <= '"+escale.getDateDebut()+"' ORDER BY dateechange DESC LIMIT 1";
        }
        return this.findOneBySql(conn, sql);
    }
    
    //Avoir la valeur de l'euro en ariary
    public Double getEuro(Double ariary) {
        return (ariary/this.getAriary());
    }
    
    //Avoir la valeur de l'euro en ariary
    public Double getAriary(Double euro) {
        return euro*this.getAriary();
    }
}
