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
import java.util.Date;
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
import model.gestionFinanciere.ValidationPrestation;
import model.gestionQuai.Proposition;
import model.profil.Users;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerValidationPrestation extends HttpServlet {

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
            Connection conn = GConnection.getSimpleConnection();
            Integer idPrestation = -1;
            if(request.getAttribute("idPrestation") != null) {
                String idPrest = (String)request.getAttribute("idPrestation");
                idPrestation = Integer.valueOf(idPrest);
            }
            else if(request.getParameter("idPrestation") != null) {
                idPrestation = Integer.valueOf(request.getParameter("idPrestation"));
            }
            else {
                throw new Exception("Erreur de requete");
            }
            Prestation prestation = new Prestation();
            prestation = prestation.findById(conn, idPrestation);
            request.setAttribute("prestation", prestation);
            Users user = new Users();
            ArrayList<Users> allUsers = user.all(conn);
            request.setAttribute("users", allUsers);

            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/validerPrestation.jsp");
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
            Connection conn = GConnection.getSimpleConnection();
            
            if(request.getParameter("profil") != null && request.getParameter("prestation") != null) {
                Users user = new Users();
                user = user.findById(conn, Integer.valueOf(request.getParameter("profil")));
                if(user.getTypeUser().getId() == 2) {
                    Prestation prestation = new Prestation();
                    prestation = prestation.findById(conn, Integer.valueOf(request.getParameter("prestation")));
                    EtatValidation etatValidation = new EtatValidation();
                    etatValidation = etatValidation.findByValue(conn, 11);
                    prestation.setEtatValidation(etatValidation);
                    prestation.editPRestation(conn);
                    HttpSession session = request.getSession();
                    Escale escale = (Escale)session.getAttribute("escale");
                    // Obtenir la date actuelle
                    Date date = new Date();

                    // Convertir la date en timestamp
                    Timestamp dateNow = new Timestamp(date.getTime());

                    ValidationPrestation validPrestation = new ValidationPrestation(dateNow, prestation);
                    validPrestation.create(conn);
                    request.setAttribute("escale", escale);
                }
                else {
                    throw new Exception("Un "+user.getTypeUser().getDesignation()+" ne peut pas valider une prestation");
                }
            }
            
            conn.close();
        } catch (Exception exe) {
           request.setAttribute("error", exe.getMessage());
           request.setAttribute("idPrestation", request.getParameter("prestation"));
           doGet(request, response);
        }  
        RequestDispatcher dispat = request.getRequestDispatcher("detailsEscal");
        dispat.forward(request, response);
    }

}
