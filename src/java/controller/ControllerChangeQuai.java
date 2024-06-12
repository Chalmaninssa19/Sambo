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
import model.gestionQuai.ChronoChangeQuai;
import model.gestionQuai.Quai;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerChangeQuai extends HttpServlet {

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
            
            HttpSession session = request.getSession();
            Escale escale = (Escale)session.getAttribute("escale");
            Quai quai = new Quai();
            ArrayList<Quai> allQuais = quai.all(conn);
            ArrayList<Quai> listsQuais = escale.getBateau().getQuaiCompatible(allQuais);
            request.setAttribute("listsQuai", listsQuais);
            request.setAttribute("idCreate",2);
            
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());        
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/changeQuai.jsp");
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
             
            if(request.getParameter("quai") != null && request.getParameter("date_debut") != null && request.getParameter("heure_debut") != null) {
                HttpSession session = request.getSession();
                Escale escale = (Escale)session.getAttribute("escale");
                escale = escale.findById(conn, escale.getId());
                Quai quaiAChanger = escale.getQuai();
                Quai quai = new Quai();
                quai = quai.findById(conn, Integer.valueOf(request.getParameter("quai")));
                Util util = new Util();
                Timestamp dateDebut = util.conversIntimestamp(request.getParameter("date_debut"), request.getParameter("heure_debut"));
                escale.setQuai(quai);
                escale.updateEscale(conn);
                ChronoChangeQuai chrono = new ChronoChangeQuai();
                chrono = chrono.getLastChangeQuai(conn, escale);
                chrono.setDateFin(dateDebut);
                chrono.updateChronoChangeQuai(conn);
                ChronoChangeQuai newChrono = new ChronoChangeQuai(escale, quai, dateDebut, null);
                newChrono.create(conn);
                TypePrestation typePrestation = new TypePrestation();
                typePrestation = typePrestation.findById(conn, 1);
                EtatValidation etatValidation = new EtatValidation();
                etatValidation = etatValidation.findById(conn, 1);
                Prestation prestation = new Prestation(escale, typePrestation, chrono.getDateDebut(), chrono.getDateFin(), quaiAChanger, 0.0, etatValidation);
                prestation.setMontant(conn);
                prestation.create(conn);
                
                request.setAttribute("escale", escale);
            }
            
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("message", exe.getMessage());
            doGet(request, response);
        }  
        RequestDispatcher dispat = request.getRequestDispatcher("detailsEscal");
        dispat.forward(request, response);
    }

}
