/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionFinanciere;

import framework.database.annotation.Champs;
import java.sql.Connection;
import java.util.ArrayList;
import model.Model;
import model.gestionBateau.Bateau;
import model.gestionBateau.CategorieBateau;
import model.gestionBateau.Pavillon;
import model.gestionQuai.Quai;

/**
 *
 * @author Chalman
 */
public class ConfigPrestation extends Model {
    @Champs(mapcol="id", name="idTypePrestation")
    private TypePrestation typePrestation;
    @Champs(mapcol="id", name="idCategorieBateau")
    private CategorieBateau categorieBateau;
    @Champs(mapcol="id", name="idPavillon")
    private Pavillon pavillon;
    @Champs(mapcol="id", name="idQuai")
    private Quai quai;
    @Champs
    private Double tarif;
    @Champs
    private Integer heureDebut;
    @Champs
    private Integer heureFin;
    @Champs
    private Integer trancheInf;
    @Champs
    private Integer trancheSup;
    private Integer dureeDiff;
    
///Encapsulation
    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }

    public CategorieBateau getCategorieBateau() {
        return categorieBateau;
    }

    public void setCategorieBateau(CategorieBateau categorieBateau) {
        this.categorieBateau = categorieBateau;
    }

    public Pavillon getPavillon() {
        return pavillon;
    }

    public void setPavillon(Pavillon pavillon) {
        this.pavillon = pavillon;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }  

    public Quai getQuai() {
        return quai;
    }

    public void setQuai(Quai quai) {
        this.quai = quai;
    }

    
    
    
    public Integer getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Integer heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }

    public Integer getTrancheInf() {
        return trancheInf;
    }

    public void setTrancheInf(Integer trancheInf) {
        this.trancheInf = trancheInf;
    }

    public Integer getTrancheSup() {
        return trancheSup;
    }

    public void setTrancheSup(Integer trancheSup) {
        this.trancheSup = trancheSup;
    }
    
    public Integer getDureeDiff() {
        if(this.getTrancheSup() == -1) {
            return 1;    
        }
        
        return this.trancheSup - this.trancheInf;
    }
    
    public void setDureeDiff(Integer dureeDiff) {
        this.dureeDiff = dureeDiff;
    }
    
///Fonctions
    //Recuperer toutes les configuration
    public ArrayList<ConfigPrestation> all(Connection conn)  throws Exception {
        return this.findAll(conn);
    }
    
    //Recuperer une configuration
    public ConfigPrestation findById(Connection conn, Integer idConfigPrestation) throws Exception {
        return this.findOneWhere(conn, "id ="+idConfigPrestation);
    }
    
    //Recuperer les configurations du prestation stationnement
    public ArrayList<ConfigPrestation> getPrestationStationnement(Connection conn) throws Exception {
        return this.findWhere(conn, "idTypePrestation = 1");
    }
    
    //Recuperer les configurations du prestation remorquage
    public ArrayList<ConfigPrestation> getPrestationRemorquage(Connection conn) throws Exception {
        return this.findWhere(conn, "idTypePrestation = 2");
    }
    
    //Avoir la configuration de station d'un bateau
    public ArrayList<ConfigPrestation> getConfigStationBateau(Connection conn, Bateau bateau) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 1 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND tranche != 1 AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de remorquage d'un bateau
    public ArrayList<ConfigPrestation> getConfigRemroquageBateau(Connection conn, Bateau bateau) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 2 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND tranche != 1 AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de station d'un bateau dans un quai
    public ArrayList<ConfigPrestation> getConfigStationBateauInQuai(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 1 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de station d'un bateau dans un quai par Minute
    public ArrayList<ConfigPrestation> getConfigStationBateauInQuaiMin(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 1 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de remorquage d'un bateau dans un quai
    public ArrayList<ConfigPrestation> getConfigRemroquageBateauInQuai(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 2 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de remorquage d'un bateau dans un quai par minute
    public ConfigPrestation getConfigRemroquageBateauInQuaiMin(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 2 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findOneBySql(conn, sql);
    }
    
     //Avoir la configuration de reparation d'un bateau
    public ArrayList<ConfigPrestation> getConfigReparationBateau(Connection conn, Bateau bateau) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 4 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND tranche != 1 AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
    //Avoir la configuration de ravitail en eau d'un bateau
    public ArrayList<ConfigPrestation> getConfigRavitailBateau(Connection conn, Bateau bateau) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 3 AND idCategorieBateau = "+bateau.getCategorie().getId()+"AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    }
    
     //Avoir la configuration de station d'un bateau dans un quai
    public ArrayList<ConfigPrestation> getConfigReparationBateauInQuai(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 4 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findBySql(conn, sql);
    } 
       
    //Avoir la configuration de station d'un bateau dans un quai par minutes
    public ConfigPrestation getConfigReparationBateauInQuaiMin(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 4 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findOneBySql(conn, sql);
    }
    
    //Avoir la configuration de remorquage d'un bateau dans un quai
    public ConfigPrestation getConfigRavitailBateauInQuai(Connection conn, Bateau bateau, Quai quai) throws Exception {
        String sql = "SELECT * FROM configPrestation WHERE idTypePrestation = 3 AND idCategorieBateau = "+bateau.getCategorie().getId()+" AND idQuai = "+quai.getId()+" AND idPavillon = "+bateau.getPavillon().getId();
        return this.findOneBySql(conn, sql);
    }
    
    //Avoir la configuration maximum
    /*public ConfigPrestation getMaxConfiguration(ArrayList<ConfigPrestation> listConfiguration) throws Exception {
        ConfigPrestation maxConfig = listConfiguration.get(0);
        for(int i = 0; i < listConfiguration.size(); i++) {
            if(maxConfig.getTranche() < listConfiguration.get(i).getTranche()) {
                maxConfig =  listConfiguration.get(i);
            }
        }
        
        return maxConfig;
    }
    
    //Avoir la configuration minimum
    public ConfigPrestation getMinConfiguration(ArrayList<ConfigPrestation> listConfiguration) throws Exception {
        ConfigPrestation minConfig = listConfiguration.get(0);
        for(int i = 0; i < listConfiguration.size(); i++) {
            if(minConfig.getTranche() > listConfiguration.get(i).getTranche()) {
                minConfig =  listConfiguration.get(i);
            }
        }
        
        return minConfig;
    }
    
    //Retourner la configuration proche du duree
    public ArrayList<ConfigPrestation> getConfigInfDuree(ArrayList<ConfigPrestation> configs, Double duree) throws Exception {
        ArrayList<ConfigPrestation> listConfs = new ArrayList<ConfigPrestation>();
        for(int i = 0; i < configs.size(); i++) {
            if(configs.get(i).getTranche() < duree) {
                listConfs.add(configs.get(i));
            }
        }
        
        return listConfs;
    }
    
    //Recuperer la configuration sur la duree
    public ConfigPrestation findConfigInDuree(Integer duree, ArrayList<ConfigPrestation> configs) throws Exception {
        ConfigPrestation confInDuree = new ConfigPrestation();
        Integer dureeDiff = calculDiff(configs);
        for(int i = 0; i < configs.size(); i++) {
            if(configs.get(i).getTranche() == duree+dureeDiff) {
                confInDuree = configs.get(i);
            }
        }
        
        return confInDuree;
    }
    
    //Calcul difference entre deux tranches
    public Integer calculDiff( ArrayList<ConfigPrestation> configs) throws Exception {
        return (configs.get(1).getTranche()-configs.get(0).getTranche());
    }
    */
    //Recuperer les configurations des dates majores
    public static ArrayList<ConfigPrestation> getConfigDate(ArrayList<ConfigPrestation> config, Integer timeDebut, Integer timeFin) {
        ArrayList<ConfigPrestation> configFind = new ArrayList<ConfigPrestation>();
        for(int i = 0; i < config.size(); i++) {
            if(config.get(i).getHeureDebut() >= timeDebut && config.get(i).getHeureFin() <= timeFin) {
                configFind.add(config.get(i));
            }
        }
        
        return configFind;
    }
    
    //Avoir la configuration du tranche
    public static ConfigPrestation getConfigTranche(ArrayList<ConfigPrestation> lists, Integer tranche) {
        for(int i = 0; i < lists.size(); i++) {
            if(tranche > lists.get(i).getTrancheInf() && tranche <= lists.get(i).getTrancheSup()) {
                return lists.get(i);
            }
            else if(tranche > lists.get(i).getTrancheInf() && lists.get(i).getTrancheSup() == -1) {
                return lists.get(i);
            }
        }
        return null;
    }
    
    //Avoir la valeur controurne d'une heure
    public static Integer getValueContourn(Integer value) {
        if(value == 0) return 24;
        if(value == 1) return 25;
        if(value == 2) return 26;
        if(value == 3) return 27;
        if(value == 4) return 28;
        if(value == 5) return 29;
        if(value == 6) return 30;
        
        return -1;
    }
}
