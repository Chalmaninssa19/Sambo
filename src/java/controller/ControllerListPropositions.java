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
import model.gestionQuai.Proposition;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerListPropositions extends HttpServlet {

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
            /*Proposition proposition = new Proposition();
            ArrayList<Proposition> listPropositions = proposition.listPropositions(conn);
            request.setAttribute("listPrevNonDisponible", proposition.getPrevisionNonDisponible());
            request.setAttribute("listPrevDecales", proposition.getPrevisionDecales());
            request.setAttribute("listPropositions", listPropositions);
*/
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("message", exe.getMessage());
            
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/listPropositions.jsp");
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
           
        } catch (Exception exe) {
          
        }  
    }

}
