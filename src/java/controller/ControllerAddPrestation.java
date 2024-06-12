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
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.gestionFinanciere.Escale;
import model.gestionFinanciere.EtatValidation;
import model.gestionFinanciere.Prestation;
import model.gestionFinanciere.TypePrestation;
import model.profil.Users;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerAddPrestation extends HttpServlet {

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
            if(request.getAttribute("message") != null) {
                new Exception((String)request.getAttribute("message"));
            }
            Connection conn = GConnection.getSimpleConnection();
            TypePrestation typePrestation = new TypePrestation();
            Users user = new Users();
            ArrayList<TypePrestation> listsTypePrestation = typePrestation.all(conn);
            ArrayList<Users> users = user.all(conn);
            HttpSession session = request.getSession();
            Escale escale = (Escale)session.getAttribute("escale");
            request.setAttribute("typePrestations", listsTypePrestation);
            request.setAttribute("profils", users);
            request.setAttribute("escale", escale);

            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());        
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/addPrestation.jsp");
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
           if(request.getParameter("profil") != null && request.getParameter("prestation") != null && request.getParameter("date_debut") != null && request.getParameter("heure_debut") != null && request.getParameter("date_fin") != null && request.getParameter("heure_fin") != null) { 
               Connection conn = GConnection.getSimpleConnection(); 
                Users user = new Users(); 
                user = user.findById(conn, Integer.valueOf(request.getParameter("profil")));
                if(user.getTypeUser().getId() == 1) {
                    HttpSession session = request.getSession();
                    Escale escale = (Escale)session.getAttribute("escale");
                    TypePrestation typePrestation = new TypePrestation();
                    typePrestation = typePrestation.findById(conn, Integer.valueOf(request.getParameter("prestation")));
                    Util util = new Util();
                    Timestamp dateDebut = util.conversIntimestamp(request.getParameter("date_debut"), request.getParameter("heure_debut"));
                    Timestamp dateFin = util.conversIntimestamp(request.getParameter("date_fin"), request.getParameter("heure_fin"));
                    EtatValidation etatValidation = new EtatValidation();
                    etatValidation = etatValidation.findByValue(conn, 1);
                    Prestation prestation = new Prestation(escale, typePrestation, dateDebut, dateFin, escale.getQuai(), 0.0, etatValidation);
                    prestation.setMontant(conn);
                    prestation.create(conn);
                    //System.out.println("Montant : "+prestation.getMontant());
               } 
               else {
                   throw new Exception("Un Profil "+user.getTypeUser().getDesignation()+" ne peut pas enregistrer une prestation");
               }
           }
        } catch (Exception exe) {
            request.setAttribute("message", exe.getMessage());
            doGet(request, response);
        }  
        doGet(request, response);
    }

}
