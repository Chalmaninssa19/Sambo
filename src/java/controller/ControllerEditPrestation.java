/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import framework.database.utilitaire.GConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.gestionBateau.Bateau;
import model.gestionBateau.Prevision;
import model.gestionFinanciere.Escale;
import model.gestionFinanciere.EtatValidation;
import model.gestionFinanciere.Prestation;
import model.gestionFinanciere.TypePrestation;
import model.gestionQuai.Proposition;
import model.gestionQuai.Quai;
import model.profil.Profil;
import model.profil.Users;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerEditPrestation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        try {  
            if(request.getParameter("idPrestation") != null) {
                Connection conn = GConnection.getSimpleConnection();
                
                //Charger les donnees a afficher
                /*Quai quai = new Quai();
                ArrayList<Quai> listsQuai = quai.all(conn);
                request.setAttribute("listsQuai", listsQuai);
                TypePrestation typePrestation = new TypePrestation();
                Profil user = new Profil();
                ArrayList<TypePrestation> listsTypePrestation = typePrestation.all(conn);
                ArrayList<Profil> profils = user.all(conn);
                request.setAttribute("typePrestations", listsTypePrestation);
                request.setAttribute("profils", profils);*/
                Users user = new Users();
                ArrayList<Users> profils = user.all(conn);
                Prestation prestation = new Prestation();
                prestation = prestation.findById(conn, request.getParameter("idPrestation"));
                String dateD = String.valueOf(prestation.getDateDebut());
                String dateF = String.valueOf(prestation.getDateFin());
                String [] dateDe = dateD.split(" ");
                String [] dateFi = dateF.split(" ");
                request.setAttribute("profils", profils);
                request.setAttribute("dateDebut", dateDe[0]);
                request.setAttribute("dateFin", dateFi[0]);
                request.setAttribute("prestation", prestation);
                conn.close();
            }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());     
            request.setAttribute("message", exe.getMessage()); 
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/editPrestation.jsp");
        dispat.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {     
           if(request.getParameter("montant") != null && request.getParameter("idPrestation") != null && request.getParameter("quai") != null && request.getParameter("profil") != null && request.getParameter("prestation") != null && request.getParameter("date_debut") != null && request.getParameter("heure_debut") != null && request.getParameter("date_fin") != null && request.getParameter("heure_fin") != null) {
                Connection conn = GConnection.getSimpleConnection(); 
                Users user = new Users(); 
                user = user.findById(conn, Integer.valueOf(request.getParameter("profil")));
                if(user.getTypeUser().getId() == 1) {
                    HttpSession session = request.getSession();
                    Escale escale = (Escale)session.getAttribute("escale");
                    TypePrestation typePrestation = new TypePrestation();
                    typePrestation = typePrestation.findById(conn, Integer.valueOf(request.getParameter("prestation")));
                    Quai quai = new Quai();
                    quai = quai.findById(conn, Integer.valueOf(request.getParameter("quai")));
                    Util util = new Util();
                    Timestamp dateDebut = util.conversIntimestamp(request.getParameter("date_debut"), request.getParameter("heure_debut"));
                    Timestamp dateFin = util.conversIntimestamp(request.getParameter("date_fin"), request.getParameter("heure_fin"));
                    Double montant = Double.valueOf(request.getParameter("montant"));
                    EtatValidation etatValidation = new EtatValidation();
                    etatValidation = etatValidation.findByValue(conn, 1);
                    Prestation prestation = new Prestation(Integer.valueOf(request.getParameter("idPrestation")),escale,typePrestation,dateDebut,dateFin,quai,montant,etatValidation); 
                    prestation.editPRestation(conn);
                    request.setAttribute("escale", escale);
               } 
               else {
                   throw new Exception("Un Profil "+user.getTypeUser().getDesignation()+" ne peut pas modifier une prestation");
               }
           }
           else {
               throw new Exception("Verifier votre requete");
           }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }  
        RequestDispatcher dispat = request.getRequestDispatcher("detailsEscal");
        dispat.forward(request, response);
    }

}
