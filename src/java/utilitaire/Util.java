/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Chalman
 */
public class Util {
    public Timestamp conversIntimestamp( String date, String time ) {   //Convertir un string en timestamp
        time = time + ":00";
        String datetime = date + " " + time;
        return Timestamp.valueOf(datetime);
    } 
    
    public long transformTimestamp(Timestamp datetime ) {   //Transforme un timestamp en nano
        return datetime.getTime();
    }
        
    public long difference2Date( long dateDebut, long dateFin ) {   //Difference entre 2 dates
        return dateFin-dateDebut;
    }
    
    public long convertinSeconde( long nano ) { //Convertit un nano en seconde
        return nano/1000;
    }
    
    public long getDureeSeconde2Date( Timestamp dateDebut, Timestamp dateFin ) {    //Retourne en seconde la difference des deux dates
        long difference = difference2Date(transformTimestamp(dateDebut), transformTimestamp(dateFin));
        return convertinSeconde(difference);
    } 
    
    public long convertSecondeinNano ( long seconde ) {  //Convertit une seconde en nano
        return seconde*1000;
    }
    
    public Timestamp getDate ( long nano ) {  //Avoir une date a partie d'un nano
       return new Timestamp(nano); 
    }
    
    public Timestamp getDateAfterDuree( Timestamp dateDebut, long dureeSeconde ) {
        long dateNano = transformTimestamp(dateDebut);
        long dureeNano = convertSecondeinNano(dureeSeconde);
        long dateAfterDuree = dateNano+dureeNano;
        
        return new Timestamp(dateAfterDuree);     
    }
    
    public static String formattedNumber( Double number ) {
        // Créer un objet DecimalFormat pour formater le nombre
        DecimalFormat decimalFormat = new DecimalFormat("#.######");

        // Formater le nombre en utilisant le modèle spécifié
        String formattedNumber = decimalFormat.format(number);
        
        return formattedNumber;
    }
    
    public static Double diffBetwenTwoTimestamp(Timestamp inf, Timestamp sup) {
        // Calcul de la différence en minutes
        long differenceInMillis = sup.getTime() - inf.getTime();
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        String duree = String.valueOf(differenceInMinutes);
        return Double.valueOf(differenceInMinutes);
    }
    
    public static Integer getHeure(Timestamp dateTime) {
  
        // Conversion du timestamp en objet Date
        Date date = new Date(dateTime.getTime());

        // Formatage de l'heure à l'aide de SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String heure = sdf.format(date);
        String [] sdfSplitter = heure.split(":");

        return Integer.valueOf(sdfSplitter[0]);
    }
    
    public static Timestamp addMinutesInTimestamp(Timestamp dateTimestamp, Integer min) {
        // Création d'une instance de Date à partir du timestamp
        Date date = new Date(dateTimestamp.getTime());

        // Utilisation de Calendar pour ajouter 15 minutes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);

        // Obtention du nouveau timestamp après l'ajout des 15 minutes
        long newTimestamp = calendar.getTimeInMillis();
        
        return new Timestamp(newTimestamp);
    }
    
    public static Double getDifference2Timestamps(Timestamp dateFin, Timestamp dateDebut) {
        long dateF = dateFin.getTime();
        long dateD = dateDebut.getTime();
        long diff = dateF - dateD;
        String differ = String.valueOf(diff);
        
        return Double.valueOf(differ);
    }
    public static Integer convertDoubleToInteger(Double value) {
        String valueString = String.valueOf(value);
        return Integer.valueOf(valueString);
    }
}
