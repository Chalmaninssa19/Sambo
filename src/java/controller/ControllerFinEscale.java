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
public class ControllerFinEscale extends HttpServlet {

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
            if(request.getAttribute("error") != null) {
                throw new Exception((String)request.getAttribute("error"));
            }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
        }
            RequestDispatcher dispat = request.getRequestDispatcher("./pages/finEscale.jsp");
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
           if(request.getParameter("date_fin") != null && request.getParameter("heure_fin") != null) {
                Connection conn = GConnection.getSimpleConnection();
                   
                Util util = new Util();
                Timestamp dateFin = util.conversIntimestamp(request.getParameter("date_fin"), request.getParameter("heure_fin"));
                HttpSession session = request.getSession();
                
                //Mettre l'escale termine
                Escale escale = (Escale)session.getAttribute("escale");
                escale = escale.findById(conn, escale.getId());
                System.out.println("Date debut : "+escale.getDateDebut());
                escale.setDateFin(dateFin);
                 System.out.println("Date fin : "+escale.getDateFin());
                escale.setDuree();
                escale.update(conn);
                
                
                ChronoChangeQuai chrono = new ChronoChangeQuai();
                chrono = chrono.getLastChangeQuai(conn, escale);
                chrono.setDateFin(dateFin);
                chrono.updateChronoChangeQuai(conn);

                //Ajouter la prestation finale
                TypePrestation typePrestation = new TypePrestation();
                typePrestation = typePrestation.findById(conn, 1);
                EtatValidation etatValidation = new EtatValidation();
                etatValidation = etatValidation.findById(conn, 1);
                Prestation prestationStation = new Prestation(escale, typePrestation, chrono.getDateDebut(), chrono.getDateFin(), escale.getQuai(), 0.0, etatValidation);
                prestationStation.setMontant(conn);
                prestationStation.create(conn);
                
                conn.setAutoCommit(false);
                conn.commit();
                request.setAttribute("escale",escale);
                
                conn.close();
           }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request,response);
        }  
        RequestDispatcher dispat = request.getRequestDispatcher("detailsEscal");
        dispat.forward(request, response);
    }

}
