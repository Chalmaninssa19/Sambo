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
import model.gestionFinanciere.Facture;
import model.gestionFinanciere.Prestation;
import model.gestionFinanciere.TypePrestation;
import model.gestionFinanciere.ValidationPrestation;
import model.gestionQuai.Proposition;
import model.profil.Users;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerIdentify extends HttpServlet {

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
            TypePrestation typePrestation = new TypePrestation();
            Users user = new Users();
            ArrayList<Users> users = user.all(conn);
            request.setAttribute("profils", users);
            request.setAttribute("id", request.getParameter("id"));
            if(request.getAttribute("error") != null) {
                throw new Exception((String)request.getAttribute("error"));
            }
            
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/identify.jsp");
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
           if(request.getParameter("profil") != null) {
               Users user = new Users();
               user = user.findById(conn, Integer.valueOf(request.getParameter("profil")));
         
               if(user.getTypeUser().getId() == 3) {
                   request.setAttribute("id", request.getParameter("id"));
               }
               else {
                   throw new Exception("Un "+user.getTypeUser().getDesignation()+" n'est pas permis a creer une facturation");
               }
           }
           else {
               throw new Exception("Completer les champs");
           }
           
           conn.close();
        } catch (Exception exe) {
          request.setAttribute("error", exe.getMessage());
          doGet(request, response);
        }  
        RequestDispatcher dispat = request.getRequestDispatcher("facture");
        dispat.forward(request, response);
    }

}
