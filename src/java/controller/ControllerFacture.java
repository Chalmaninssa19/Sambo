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
import model.gestionFinanciere.CoursEchange;
import model.gestionFinanciere.Escale;
import model.gestionFinanciere.Facture;
import model.gestionFinanciere.Prestation;

/**
 *
 * @author Chalman
 */
public class ControllerFacture extends HttpServlet {

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
            Integer id = Integer.valueOf((String)request.getAttribute("id"));
            if(id == 1) {
                HttpSession session = request.getSession();
                Escale escale = (Escale)session.getAttribute("escale");
                ArrayList<Prestation> prestations = escale.getPrestations(conn);
                ArrayList<Facture> factures = escale.getFacture(conn, prestations);
                CoursEchange coursEchange = new CoursEchange();
                coursEchange = coursEchange.getEchangeProcheEscale(conn, escale);
                Date date = new Date();
                Timestamp dateNow = new Timestamp(date.getTime());
                request.setAttribute("escale", escale);
                request.setAttribute("factures", factures);
                request.setAttribute("dateNow", dateNow);
                request.setAttribute("montantTotal",escale.getMontantTotal(factures));
                request.setAttribute("montantEuro",coursEchange.getEuro(escale.getMontantTotal(factures)));
            }
            else if(id == 3) {
                HttpSession session = request.getSession();
                Escale escale = (Escale)session.getAttribute("escale");
                Facture facture = new Facture();
                ArrayList<Facture> factures = facture.getMyFacture(conn, escale);
                CoursEchange coursEchange = new CoursEchange();
                coursEchange = coursEchange.getEchangeProcheEscale(conn, escale);
                Date date = new Date();
                Timestamp dateNow = new Timestamp(date.getTime());
                request.setAttribute("escale", escale);
                request.setAttribute("factures", factures);
                request.setAttribute("dateNow", dateNow);
                request.setAttribute("montantTotal",escale.getMontantTotal(factures));
                request.setAttribute("montantEuro",coursEchange.getEuro(escale.getMontantTotal(factures)));
                RequestDispatcher dispat = request.getRequestDispatcher("./pages/voirFacture.jsp");
                dispat.forward(request, response);
            }
            else {
                HttpSession session = request.getSession();
                Escale escale = (Escale)session.getAttribute("escale");
                ArrayList<Prestation> prestations = escale.getPrestations(conn);
                ArrayList<Facture> factures = escale.getFacture(conn, prestations);
                escale.validerFacture(conn, escale, factures);
                request.setAttribute("escale",escale);
                RequestDispatcher dispat = request.getRequestDispatcher("detailsEscal");
                dispat.forward(request, response);
            }
           
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/factureEscale.jsp");
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
           if(request.getAttribute("id") != null) {
               request.setAttribute("id", request.getAttribute("id"));
           }
        } catch (Exception exe) {
          
        }  
        doGet(request, response);
    }

}
