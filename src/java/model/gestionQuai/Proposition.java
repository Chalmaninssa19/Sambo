/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.gestionQuai;

import framework.database.annotation.Champs;
import framework.database.utilitaire.GConnection;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import model.Model;
import model.gestionBateau.Bateau;
import model.gestionBateau.Prevision;
import model.gestionFinanciere.Disponibilite;
import model.gestionFinanciere.Escale;
import model.gestionFinanciere.Prestation;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class Proposition extends Prevision {
    private Prevision prevision;
    private Quai quai;
    private Double montant;
    private ArrayList<Prevision> previsionNonDisponible = new ArrayList<Prevision>();
    private ArrayList<PrevisionDecale> previsionDecales = new ArrayList<PrevisionDecale>();
    private Double dureeAttente;
    private String reference;
    
///Encapsulation
    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
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

    public ArrayList<Prevision> getPrevisionNonDisponible() {
        return previsionNonDisponible;
    }

    public void setPrevisionNonDisponible(ArrayList<Prevision> previsionNonDisponible) {
        this.previsionNonDisponible = previsionNonDisponible;
    }

    public ArrayList<PrevisionDecale> getPrevisionDecales() {
        return previsionDecales;
    }

    public void setPrevisionDecales(ArrayList<PrevisionDecale> listDecales) {;
        this.previsionDecales = listDecales;
    }

    public Double getDureeAttente() {
        return dureeAttente;
    }

    public void setDureeAttente(Double dureeAttente) {
        this.dureeAttente = dureeAttente;
    }

    public String getReference() {
        return reference;
    }

    public void setReference() {
        // Conversion du timestamp en objet Date
        Date date = new Date(this.getPrevision().getDateDebut().getTime());

        // Création d'une instance de Calendar et configuration de la date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extraction de l'heure et du mois
        int mois = calendar.get(Calendar.MONTH) + 1; // Les mois sont indexés à partir de zéro, donc on ajoute 1
        int year = calendar.get(Calendar.YEAR);
        String ref = "PROP0"+mois+""+year+"00"+this.getPrevision().getId();
        this.reference = ref;
    }
    
    
///Constructors
    public Proposition() {
    }

    public Proposition(Prevision prevision, Quai quai, Double montant, Double dureeAttente) {
        this.prevision = prevision;
        this.quai = quai;
        this.montant = montant;
        this.dureeAttente = dureeAttente;
        this.setReference();
    }
    
///Fonctions
    //Creer une prevision dans la base
    public void create (Connection con) throws Exception {
        boolean b = true ;
        try{
                if (con==null){
                    con = GConnection.getSimpleConnection();
                    b = false ;
                }
                con.setAutoCommit(false);
                Proposition proposition = new Proposition();
                int propId = this.sequence("idPropositionSeq",con);
                this.setId(propId);
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
    
    //Listes des propositions pour chaque prevision
   /* public ArrayList<Proposition> listPropositions(Connection conn) throws Exception {
        //Initialisation
        Prevision prevision = new Prevision();
        Quai quai = new Quai();
        ArrayList<Prevision> previsionsTrie = prevision.listPrevisionTrierByDateArrive(conn);   //Listes des previsions tries par date
        ArrayList<Proposition> listsProposition = new ArrayList<>();    
        ArrayList<Quai> listQuais = quai.listQuaiTrierByProfondeur(conn); //Listes de toutes les quais disponibles
        
        //Traitement
        for(int i = 0; i < previsionsTrie.size(); i++) {
            if(findQuaiOptimise(conn, previsionsTrie.get(i), listQuais, listsProposition) != null) {
               Quai quaiOptimise = findQuaiOptimise(conn, previsionsTrie.get(i), listQuais, listsProposition); //Trouver un quai optimise pour une prevision
               Prestation prestation = new Prestation();
               prestation.setPrixStation(conn, previsionsTrie.get(i), quaiOptimise);
               prestation.setPrixRemorquage(conn, previsionsTrie.get(i), quaiOptimise); 
               quaiOptimise.setMontant(prestation.getPrixStationnement());
               Double montantPrevision = quaiOptimise.getMontant() + prestation.getPrixRemorquage();
              
               if(isPrevisionInListDecales(previsionsTrie.get(i)) == null) {    //Si la prevision n'est pas dans la liste des decales 
                   Proposition newProposition = new Proposition(previsionsTrie.get(i), quaiOptimise, montantPrevision, 0.0);  //La nouvelle proposition
                    listsProposition.add(newProposition);   //Ajouter la nouvelle proposition dans la liste des propositions  
               }
               else {   //La prevision est dans la liste des descales
                    PrevisionDecale pd = isPrevisionInListDecales(previsionsTrie.get(i));
                    System.out.println("io date : "+previsionsTrie.get(i).getDateDebut()+" et "+pd.getDateFin());
                    Double dureeAttente = Util.diffBetwenTwoTimestamp(previsionsTrie.get(i).getDateDebut(), pd.getDateFin());
                    System.out.println("duree : "+dureeAttente);
                    if(dureeAttente < 0) {
                        Proposition newProposition = new Proposition(previsionsTrie.get(i), quaiOptimise, montantPrevision, 0.0);  //La nouvelle proposition
                        //System.out.println("props : "+newProposition.getPrevision().getId());
                        listsProposition.add(newProposition);   //Ajouter la nouvelle proposition dans la liste des propositions 
                    }
                    else {
                        Proposition newProposition = new Proposition(previsionsTrie.get(i), quaiOptimise, montantPrevision, dureeAttente);  //La nouvelle proposition
                        System.out.println("props : "+newProposition.getBateau().getNom());
                        listsProposition.add(newProposition);   //Ajouter la nouvelle proposition dans la liste des propositions 
                    }
               }
            }
            else {
                this.getPrevisionNonDisponible().add(previsionsTrie.get(i));
            }
        }
        //Resultat
        return listsProposition;
    }
    */
    //Est ce qu'une prevision est dans la liste des decales
    public PrevisionDecale isPrevisionInListDecales(Prevision prevision) throws Exception {
        for(int i = 0; i < this.getPrevisionDecales().size(); i++) {
            if(this.getPrevisionDecales().get(i).getPrevisionDecale().getId() == prevision.getId()) {
                return this.getPrevisionDecales().get(i);
            }
        }
        return null;
    }
    //Est ce que le quai est deja propose
    public boolean isQuaiProposed(Quai quaiProposed, ArrayList<Proposition> listProposes ) throws Exception {
        for(int i = 0; i < listProposes.size(); i++) {
            if(quaiProposed.getId() == listProposes.get(i).getQuai().getId()) { //Si le quai n'est pas encore propose
                return true;
            }   
        }
        return false;
    }
    
    //Optimiser la recherche de quai par le minimum parmi les disponibles
    /*public Quai findQuaiOptimise(Prevision prevision, ArrayList<Quai> listQuais, ArrayList<Proposition> listProposes) throws Exception {
        ArrayList<Quai> quaiDisponible = new ArrayList<Quai>();
        for(int i = 0; i < listQuais.size(); i++) {
            //Si le quai n'est pas encore propose et le profondeur du bateau est compatible avec le profondeur du quai
            if(isQuaiProposed(listQuais.get(i), listProposes) == false && (listQuais.get(i).getProfondeur() - prevision.getBateau().getProfondeur()) > 0) {
                quaiDisponible.add(listQuais.get(i));
            }
        }
      
        //Si aucun quai n'est disponible pour la prevision
        if(quaiDisponible.size() == 0) {
            //Parmi la liste deja proposes s'il existe un quai qui lui est compatible, on le decale
            ArrayList<Proposition> propositionsCompatible = this.findCompatible(prevision, listProposes);
            if(propositionsCompatible.size() == 0) {    //S'il n'y a aucun quai disponible 
                this.getPrevisionNonDisponible().add(prevision);
            }
            else {
                Prevision previsionAttendre = this.previsionAttendre(propositionsCompatible);
                this.getPrevisionDecales().add(new PrevisionDecale(prevision, previsionAttendre));
            }
        }
             
        return findQuaiMinProfondeur(quaiDisponible);   
    }*/
    
    //Est ce que le quai et la prevision sont compatible
    public boolean isQuaiCompatible(Prevision prevision, Quai quai) {
        if(quai.getProfondeur() - prevision.getBateau().getProfondeur() > 0) {
            return true;
        }
        return false;
    }
    
    //Rechercher un quai libre dans une liste
    public Quai findQuaiLibreInList(Quai quai, ArrayList<Quai> lists, Prevision prevision) throws Exception {
        for(int i = 0; i < lists.size(); i++) {
            if(lists.get(i).getIsDispo() == true && lists.get(i).getId() != quai.getId() && isQuaiCompatible(prevision, lists.get(i)) == true) {
                return lists.get(i);
            }
        }
        return null;
    }
    
    //Est ce que le quai est dans la list
    public boolean isQuaiInList(Quai quai, ArrayList<Proposition> listProposes) throws Exception {
        for(int i = 0; i < listProposes.size(); i++) {
            if(listProposes.get(i).getQuai().getId() == quai.getId()) {
                return true;
            }
        }
        return false;
    }
    
    //Est ce que le quai est dans la liste
    public Proposition isQuaiInListing(Quai quai, ArrayList<Proposition> listProposes) throws Exception {
        for(int i = 0; i < listProposes.size(); i++) {
            if(listProposes.get(i).getQuai().getId() == quai.getId()) {
                return listProposes.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Proposition> listWhereWeFindQuai(Quai quai, ArrayList<Proposition> listPropos) throws Exception {
        ArrayList<Proposition> newLists = new ArrayList<>();
        for(int i = 0; i < listPropos.size(); i++) {
            if(listPropos.get(i).getQuai().getId() == quai.getId()) {
                newLists.add(listPropos.get(i));
            }
        }
        
        return newLists;
    }
    
    public Proposition getPropMaxDuree(ArrayList<Proposition> listsProps) throws Exception {
        Proposition prop = listsProps.get(0);
        for(int i = 1; i < listsProps.size(); i++) {
            if(prop.getDureeAttente() < listsProps.get(i).getDureeAttente()) {
                prop = listsProps.get(i);
            }
        }
        return prop;
    }
    
    public Proposition getPropMinDuree(ArrayList<Proposition> listsProps) throws Exception {
        Proposition prop = listsProps.get(0);
        for(int i = 1; i < listsProps.size(); i++) {
            if(prop.getDureeAttente() < listsProps.get(i).getDureeAttente()) {
                prop = listsProps.get(i);
            }
        }

        return prop;
    }
    
    //Optimiser la recherche de quai par le minimum parmi les disponibles
    public Quai findQuaiOptimise(Connection conn, Prevision prevision, ArrayList<Quai> listQuais, ArrayList<Proposition> listProposes) throws Exception {
        for(int i = 0; i < listQuais.size(); i++) {
            if(isQuaiInList(listQuais.get(i), listProposes) == false) { //Est ce que le quai n'est pas encore propose
                if(isQuaiCompatible(prevision, listQuais.get(i)) == true) {
                    if(listQuais.get(i).getIsDispo() == false) {    //Si le quai n'est pas libre
                        Disponibilite dispos = new Disponibilite();
                        Escale escale = dispos.getEscale(conn, listQuais.get(i));
                        //Mbola misy quai libre hafa ve any
                        Quai quaiLibre = findQuaiLibreInList(listQuais.get(i), listQuais, prevision);
                        if(quaiLibre != null) {
                            return quaiLibre;
                        }
                        else {
                            //Milahatra zany lay prevision
                            this.getPrevisionDecales().add(new PrevisionDecale(prevision, escale.getDateFin()));
                        }
                    }
                    else {  //Si le quai est disponible
                        return listQuais.get(i);
                    }
                }
            }
        }   

        ArrayList<Proposition> propsAAttendre = new ArrayList<>();
        //Donc les quais sont remplis ou tous incompatibles
        for(int i = 0; i < listQuais.size(); i++) {
            if(isQuaiInList(listQuais.get(i), listProposes) == true) { //Est ce que le quai est deja propose
                if(isQuaiCompatible(prevision, listQuais.get(i)) == true) { //Est ce que le quai est compatible
                    propsAAttendre.add(isQuaiInListing(listQuais.get(i), listProposes));                          
                }
            }
        }
        System.out.println("Misy : "+propsAAttendre.size());
        if(propsAAttendre!=null) {
            Proposition propMax = getPropMinDuree(propsAAttendre);
            System.out.println("tafiditra : "+propMax.getDateDebut());
            this.getPrevisionDecales().add(new PrevisionDecale(prevision, propMax.getPrevision().getDateFin()));
            return propMax.getQuai();
        }

        return null;
    }
    
    //Trouver le quai qui a le profondeur minimum parmi les quais disponibles
    public Quai findQuaiMinProfondeur(ArrayList<Quai> quaiDisponibles) throws Exception {
        Quai quaiMin = null;
        if(quaiDisponibles.size() > 0 ) {
            quaiMin = quaiDisponibles.get(0);
            for(int i = 0; i < quaiDisponibles.size(); i++) {
                if(quaiMin.getProfondeur() > quaiDisponibles.get(i).getProfondeur()) {
                    quaiMin = quaiDisponibles.get(i);
                }
            }
        }
        
        return quaiMin;
    }
    
    //Rechercher les quais qui sont compatibles a une prevision parmi les proposes
    public ArrayList<Proposition> findCompatible(Prevision prevision, ArrayList<Proposition> listProposes) throws Exception {
        ArrayList<Proposition> proposes = new ArrayList<>();
        for(int i = 0; i < listProposes.size(); i++) {
            if(listProposes.get(i).getQuai().getProfondeur() - prevision.getBateau().getProfondeur() > 0) {
                proposes.add(listProposes.get(i));
            }
        }
        
        return proposes;
    }
    
    //Rechercher la prevision a attendre
    public Prevision previsionAttendre(ArrayList<Proposition> listsCompatible) throws Exception {
        Prevision previsionAttendre = listsCompatible.get(0).getPrevision();
        for(int i = 1; i < listsCompatible.size(); i++) {
            if(previsionAttendre.getDureeEscale() > listsCompatible.get(i).getPrevision().getDureeEscale()) {
                previsionAttendre = listsCompatible.get(i).getPrevision();
            }
        }
        
        return previsionAttendre;
    }
}
