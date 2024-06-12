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
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class Prestation extends Model {
    @Champs(mapcol="id", name="idEscale")
    private Escale escale;
    @Champs
    private String reference;
    @Champs(mapcol="id", name="idTypePrestation")
    private TypePrestation typePrestation;
    @Champs
    private Timestamp dateDebut;
    @Champs
    private Timestamp dateFin;
    @Champs
    private Double duree;
    @Champs(mapcol="id", name="idQuai")
    private Quai quai;
    @Champs
    private Double montant;
    @Champs(mapcol="id", name="idEtatValidation")
    private EtatValidation etatValidation;
    
///Encapsulation
    public Escale getEscale() {
        return escale;
    }

    public void setEscale(Escale escale) {
        this.escale = escale;
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
        
        String ref = "PREST"+year+""+mois+""+day+""+this.getEscale().getId();
        this.reference = ref;
    }

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
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

    public Double getDuree() {
        return duree;
    }

    public void setDuree() throws Exception {
        // Calcul de la différence en minutes
        long differenceInMillis = getDateFin().getTime() - getDateDebut().getTime();
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        Double duree = Double.valueOf(differenceInMinutes);
        if(duree >= 0) {
            this.duree = duree;
        }
        else {
            throw new Exception("Date fin doit etre superieur a "+this.getDateDebut());
        }
    }

    public Quai getQuai() {
        return quai;
    }
    public void setQuai(Quai quai) {
        this.quai = quai;
    }

    public Double getMontant() {
        return montant;
    }
    
    public void setMontant(Double montant) {
        this.montant = montant;
    }
    public void setMontant(Connection conn) throws Exception {
        ConfigPrestation configPrestation = new ConfigPrestation();
        if(this.getTypePrestation().getId() == 1) {
            ArrayList<ConfigPrestation> configStation = configPrestation.getConfigStationBateauInQuai(conn,this.escale.getBateau(),this.getEscale().getQuai());
            Double montantFinal = this.setMontantStationMajore(configStation,0,this.getDateDebut(),0, this.getDuree().intValue(), configStation.get(0).getDureeDiff(), 0.0, configStation.get(0).getDureeDiff());
            this.setMontant(montantFinal);
        } 
        else if(this.getTypePrestation().getId() == 2) {
            ArrayList<ConfigPrestation> configRemorque = configPrestation.getConfigRemroquageBateauInQuai(conn,this.escale.getBateau(),this.getEscale().getQuai());
            Double montantFinal = this.setMontantStationMajore(configRemorque,0,this.getDateDebut(),0, this.getDuree().intValue(), configRemorque.get(0).getDureeDiff(), 0.0, configRemorque.get(0).getDureeDiff());
            this.setMontant(montantFinal);
        } 
        else if(this.getTypePrestation().getId() == 3) {
            ConfigPrestation configRavitailEau = configPrestation.getConfigRavitailBateauInQuai(conn, this.getEscale().getBateau(), this.getEscale().getQuai());
          Double montantFinal = this.setMontantFixe(configRavitailEau, this.getEscale().getBateau());
          this.setMontant(montantFinal);
        } 
        else if(this.getTypePrestation().getId() == 4) {
            ArrayList<ConfigPrestation> configReparation = configPrestation.getConfigReparationBateauInQuai(conn,this.escale.getBateau(),this.getEscale().getQuai());
            Double montantFinal = this.setMontantStationMajore(configReparation,0,this.getDateDebut(),0, this.getDuree().intValue(), configReparation.get(0).getDureeDiff(), 0.0, configReparation.get(0).getDureeDiff());
            this.setMontant(montantFinal);
        } 
    }

    public EtatValidation getEtatValidation() {
        return etatValidation;
    }
    public void setEtatValidation(EtatValidation etatValidation) {
        this.etatValidation = etatValidation;
    }
    
    
///Constructors
    public Prestation() {
    }

    public Prestation(Escale escale, TypePrestation typePrestation, Timestamp dateDebut, Timestamp dateFin, Quai quai, Double montant, EtatValidation etatValidation) throws Exception {
        try {
            this.escale = escale;
            this.typePrestation = typePrestation;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.quai = quai;
            this.setDuree();
            this.montant = montant;
            this.setReference();
            this.etatValidation = etatValidation;
           
        } catch (Exception e) {
            throw e;
        }
    }

    public Prestation(Integer idPrestation, Escale escale, TypePrestation typePrestation, Timestamp dateDebut, Timestamp dateFin, Quai quai, Double montant, EtatValidation etatValidation) throws Exception {
        try {
            this.escale = escale;
            this.typePrestation = typePrestation;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.quai = quai;
            this.setDuree();
            this.montant = montant;
            this.setReference();
            this.setId(idPrestation);
            this.etatValidation = etatValidation; 
        } catch(Exception e) {
            throw e;
        }
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
                int zoneId = this.sequence("idPrestationSeq",con);
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
    public ArrayList<Prestation> getMyPrestations(Connection conn, Escale escale)  throws Exception { 
        return this.findWhere(conn, "idEscale = "+escale.getId());
    }
    
    //Recuperer un escale par son id
    public Prestation findById(Connection conn, Integer idEscale) throws Exception {
        return this.findOneWhere(conn, "id ="+idEscale);
    }
    
    //Modifier une prestation
    public void editPRestation(Connection conn) throws Exception {
        this.update(conn);
        //conn.commit();
    }
    
    //Supprimer une prestation
     public void deletePRestation(Connection conn) throws Exception {
        this.delete(conn);
        conn.setAutoCommit(false);
        conn.commit();
    }
     
    //Est ce que la prestation est valider
     public boolean isValider() {
         if(this.getEtatValidation().getValue() <=11 && this.getEtatValidation().getValue() > 1) {
             return true;
         }
         return false;
    }
     
    //Montant stationnement
    /* public Double setMontantStationnement(Connection conn, Escale escale) throws Exception {
        Double montantStationnement = 0.0;
        ConfigPrestation configPrestation = new ConfigPrestation();
        ArrayList<ConfigPrestation> config = configPrestation.getConfigStationBateauInQuai(conn, escale.getBateau(), this.getQuai());
        ConfigPrestation configMax = configPrestation.getMaxConfiguration(config);
        if(this.getDuree() < configMax.getTranche()) {  //Si la duree est inferieur au tranche maximum dans la configuration
            ArrayList<ConfigPrestation> configInDuree = configPrestation.getConfigInfDuree(config, this.getDuree());    //Prendre toutes les configurations inferieurs au duree
            if(configInDuree.size() > 0) {
              ConfigPrestation configMaxIn = configPrestation.getMaxConfiguration(configInDuree); //Le maximum parmi les recupereres
              ConfigPrestation configuration = configPrestation.findConfigInDuree(configMaxIn.getTranche(), config);  //Retrouver la configuration du duree
              configInDuree.add(configuration);   //Ajouter dans la liste;
              montantStationnement = this.getMontantTotal(configInDuree); //Le montant total  
            } 
            else {
                //Prendre la tranche minimum;
                ConfigPrestation minConfig = configPrestation.getMinConfiguration(config);
                montantStationnement = minConfig.getTarif();
            }
        }
        else {
            Double diff = this.getDuree() - configMax.getTranche();
            ConfigPrestation configParMin = configPrestation.getConfigStationBateauInQuaiMin(conn, escale.getBateau(), this.getQuai());
            Double valueLineaire = configParMin.getTarif()*diff;
            Double montantNonLineaire = this.getMontantTotal(config);
            montantStationnement = valueLineaire+montantNonLineaire;
        }
        
        return montantStationnement;
     }
     
    //Montant remorquage
    public Double setMontantRemorquage(Connection conn, Escale escale) throws Exception {
        Double montantRemorque = 0.0;
        ConfigPrestation configPrestation = new ConfigPrestation();
        ArrayList<ConfigPrestation> config = configPrestation.getConfigRemroquageBateauInQuai(conn, escale.getBateau(), this.getQuai());
        ConfigPrestation configMax = configPrestation.getMaxConfiguration(config);
        Double duree = escale.getBateau().getDureeRemorquage();
        if(duree < configMax.getTranche()) {  //Si la duree est inferieur au tranche maximum dans la configuration
            ArrayList<ConfigPrestation> configInDuree = configPrestation.getConfigInfDuree(config, duree);    //Prendre toutes les configurations inferieurs au duree
            if(configInDuree.size() > 0) {
              ConfigPrestation configMaxIn = configPrestation.getMaxConfiguration(configInDuree); //Le maximum parmi les recupereres
              ConfigPrestation configuration = configPrestation.findConfigInDuree(configMaxIn.getTranche(), config);  //Retrouver la configuration du duree
              configInDuree.add(configuration);   //Ajouter dans la liste;
              montantRemorque = this.getMontantTotal(configInDuree); //Le montant total  
            } 
            else {
                //Prendre la tranche minimum;
                ConfigPrestation minConfig = configPrestation.getMinConfiguration(config);
                montantRemorque = minConfig.getTarif();
            }
        }
        else {
            Double diff = duree - configMax.getTranche();
            ConfigPrestation configParMin = configPrestation.getConfigRemroquageBateauInQuaiMin(conn, escale.getBateau(), this.getQuai());
            Double valueLineaire = configParMin.getTarif()*diff;
            Double montantNonLineaire = this.getMontantTotal(config);
            montantRemorque = valueLineaire+montantNonLineaire;
        }
        
        return montantRemorque;
     }
     
    //Montant reparation
    public Double setMontantReparation(Connection conn, Escale escale) throws Exception {
        Double montantReparation = 0.0;
        ConfigPrestation configPrestation = new ConfigPrestation();
        ArrayList<ConfigPrestation> config = configPrestation.getConfigReparationBateauInQuai(conn, escale.getBateau(), this.getQuai());
        ConfigPrestation configMax = configPrestation.getMaxConfiguration(config);
        if(this.duree < configMax.getTranche()) {  //Si la duree est inferieur au tranche maximum dans la configuration
            ArrayList<ConfigPrestation> configInDuree = configPrestation.getConfigInfDuree(config, this.duree);    //Prendre toutes les configurations inferieurs au duree
             if(configInDuree.size() > 0) {
              ConfigPrestation configMaxIn = configPrestation.getMaxConfiguration(configInDuree); //Le maximum parmi les recupereres
              ConfigPrestation configuration = configPrestation.findConfigInDuree(configMaxIn.getTranche(), config);  //Retrouver la configuration du duree
              configInDuree.add(configuration);   //Ajouter dans la liste;
              montantReparation = this.getMontantTotal(configInDuree); //Le montant total  
            } 
            else {
                //Prendre la tranche minimum;
                ConfigPrestation minConfig = configPrestation.getMinConfiguration(config);
                montantReparation = minConfig.getTarif();
            }
        }
        else {
            Double diff = this.duree - configMax.getTranche();
            ConfigPrestation configParMin = configPrestation.getConfigReparationBateauInQuaiMin(conn, escale.getBateau(), this.getQuai());
            Double valueLineaire = configParMin.getTarif()*diff;
            Double montantNonLineaire = this.getMontantTotal(config);
            montantReparation = valueLineaire+montantNonLineaire;
        }
        
        return montantReparation;
     }
     
    //Montant ravitail en eau
    public Double setMontantRavitailEnEau(Connection conn, Escale escale) throws Exception {
        Double montantRemorque = 0.0;
        ConfigPrestation configPrestation = new ConfigPrestation();
        ConfigPrestation config = configPrestation.getConfigRavitailBateauInQuai(conn, escale.getBateau(), this.getQuai());
        montantRemorque = config.getTarif();
    
        return montantRemorque;
     }
    */
    //Sommer les montants
    public Double getMontantTotal(ArrayList<ConfigPrestation> listConfs) throws Exception {
        Double sum = 0.0;
        for(int i = 0; i < listConfs.size(); i++) {
            sum += listConfs.get(i).getTarif();
        }
        
        return sum;
    } 
    
    //Calcul montant station avec majoration
    public Double setMontantStationMajore(ArrayList<ConfigPrestation> listConfig,Integer souvenir, Timestamp dateDebut,Integer dureeVerify, Integer dureeFinal, Integer dureeIncrement, Double montant, Integer dureeDiff) throws Exception {
        Util util = new Util();
        Double montantFinal = 0.0;
        if(dureeVerify >= dureeFinal) {
            montantFinal = montant;
        }
        else {
            Integer timeDate = util.getHeure(dateDebut); //Avoir l'heure du date
            if(timeDate >= 0 && timeDate < 18) {
                if(souvenir == 0) {
                    dureeIncrement = listConfig.get(0).getDureeDiff();
                }
                souvenir = 0;
                ArrayList<ConfigPrestation> configs = ConfigPrestation.getConfigDate(listConfig, 0, 18); //Avoir la configuration des deux marges
                if(ConfigPrestation.getConfigTranche(configs, dureeIncrement) != null) {
                    ConfigPrestation configTranche = ConfigPrestation.getConfigTranche(configs, dureeIncrement);
                    dureeDiff = configTranche.getDureeDiff();
                    dureeVerify = dureeVerify+dureeDiff;
                    Timestamp newDate = util.addMinutesInTimestamp(dateDebut, dureeDiff);  //Ajouter dureeDiff minutes a la dateDebut
                    dureeIncrement = dureeIncrement + dureeDiff;
                    montant = montant + configTranche.getTarif();
                    return setMontantStationMajore(listConfig,souvenir, newDate, dureeVerify, dureeFinal, dureeIncrement, montant, dureeDiff);
                }
                else {
                    throw new Exception("Impossible de trouver la tranche correspondant");
                }
            }
            else {
                if(souvenir == 0) {
                    dureeIncrement = listConfig.get(0).getDureeDiff();
                }
                souvenir = 18;
                ArrayList<ConfigPrestation> configs = ConfigPrestation.getConfigDate(listConfig, 18, 0); //Avoir la configuration des deux marges
                if(ConfigPrestation.getConfigTranche(configs, dureeIncrement) != null) {    //Si la configuration du tranche n'est pas null
                    ConfigPrestation configTranche = ConfigPrestation.getConfigTranche(configs, dureeIncrement);
                    dureeDiff = configTranche.getDureeDiff();
                    dureeVerify = dureeVerify+dureeDiff;
                    Timestamp newDate = util.addMinutesInTimestamp(dateDebut, dureeDiff);  //Ajouter dureeDiff minutes a la dateDebut
                    dureeIncrement = dureeIncrement + dureeDiff;
                    montant = montant + configTranche.getTarif();
                    return setMontantStationMajore(listConfig,souvenir, newDate, dureeVerify, dureeFinal, dureeIncrement, montant, dureeDiff);
                }
                else {
                    throw new Exception("Impossible de trouver la tranche correspondant");
                }
            }
        }
        
        return montantFinal;
    }
    
    //Calcul montant remorquage
    public Double setMontantNonMajore(ArrayList<ConfigPrestation> listConfig,Timestamp dateDebut,Integer dureeVerify, Integer dureeFinal, Integer dureeIncrement, Double montant, Integer dureeDiff) throws Exception {
        Util util = new Util();
        Double montantFinal = 0.0;
        if(dureeVerify >= dureeFinal) {
            montantFinal = montant;
        }
        else {
                if(ConfigPrestation.getConfigTranche(listConfig, dureeIncrement) != null) {
                    ConfigPrestation configTranche = ConfigPrestation.getConfigTranche(listConfig, dureeIncrement);
                    dureeDiff = configTranche.getDureeDiff();
                    dureeVerify = dureeVerify+dureeDiff;
                    Timestamp newDate = util.addMinutesInTimestamp(dateDebut, dureeDiff);  //Ajouter dureeDiff minutes a la dateDebut
                    dureeIncrement = dureeIncrement + dureeDiff;
                    montant = montant + configTranche.getTarif();
                    return setMontantNonMajore(listConfig,newDate, dureeVerify, dureeFinal, dureeIncrement, montant, dureeDiff);
                }
                else {
                    throw new Exception("Impossible de trouver la tranche correspondant");
                }
            }
        
        return montantFinal;
    }
    
    //Calcul du prestation avec montant fixe
    public Double setMontantFixe(ConfigPrestation configPrestationFixe, Bateau bateau) {
        Double montant = configPrestationFixe.getTarif()*this.getDuree();
        return montant;
    }
}
