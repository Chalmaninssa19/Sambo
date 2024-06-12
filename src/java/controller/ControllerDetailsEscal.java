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
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.gestionFinanciere.Escale;
import model.gestionFinanciere.Prestation;

/**
 *
 * @author Chalman
 */
public class ControllerDetailsEscal extends HttpServlet {

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
            Integer idEscale = 0;
            if(request.getAttribute("escale") != null) {
                Escale escale = (Escale)request.getAttribute("escale");
                idEscale = escale.getId();
            }
            else if (request.getParameter("idEscale") != null){ 
                idEscale = Integer.valueOf(request.getParameter("idEscale"));
            }
            else {
                throw new Exception("Erreur : Impossible d'etablir la requete qui est nulle");
            }
            
            Escale escale = new Escale();
            Prestation prestation = new Prestation();
            escale = escale.findById(conn, idEscale) ;
            HttpSession session = request.getSession();
            session.setAttribute("escale", escale);
            ArrayList<Prestation> listPrestations = prestation.getMyPrestations(conn, escale);
            boolean isFacture = escale.isFacturer(conn);
            System.out.println("esc facturer : "+isFacture);
            request.setAttribute("listPrestations", listPrestations);
            //request.setAttribute("bateau", escale.getBateau());
            request.setAttribute("isFacture", isFacture);
            request.setAttribute("escale", escale);
            
            conn.close();
        } catch (Exception exe) {
            //exe.printStackTrace();
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/detailsEscal.jsp");
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
          if(request.getAttribute("escale") != null) {
              request.setAttribute("escale", request.getAttribute("escale"));
          }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }  
        doGet(request, response);
    }

}
