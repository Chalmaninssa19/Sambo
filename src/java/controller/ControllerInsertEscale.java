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
import model.gestionBateau.Bateau;
import model.gestionFinanciere.Escale;
import model.gestionQuai.ChronoChangeQuai;
import model.gestionQuai.Quai;
import utilitaire.Util;

/**
 *
 * @author Chalman
 */
public class ControllerInsertEscale extends HttpServlet {

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
                Connection connex = GConnection.getSimpleConnection();
                
                if(request.getAttribute("error") != null) {
                    request.setAttribute("error", request.getAttribute("error"));
                }
             
                if(request.getAttribute("idCreate") == null) {
                    Bateau bateau = new Bateau();
                    ArrayList<Bateau> listsBateau = bateau.all(connex);
                    request.setAttribute("listsBateau", listsBateau);
                    request.setAttribute("idCreate",1);
                }
                else {
                    Quai quai = new Quai();
                    ArrayList<Quai> allQuais = quai.all(connex);
                    HttpSession session = request.getSession();
                    Escale escale = (Escale)session.getAttribute("createEscale");
                    ArrayList<Quai> listsQuais = escale.getBateau().getQuaiCompatible(allQuais);
                    request.setAttribute("listsQuai", listsQuais);
                    request.setAttribute("idCreate",2);
                }
                connex.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/createEscale.jsp");
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
            Connection connex = GConnection.getSimpleConnection();
            if(request.getParameter("bateau") != null && request.getParameter("date_debut") != null &&  request.getParameter("heure_debut") != null) {
                Util util = new Util();          
                Integer idBateau = Integer.valueOf(request.getParameter("bateau"));
                Timestamp dateDebut = util.conversIntimestamp(request.getParameter("date_debut"), request.getParameter("heure_debut"));
                Bateau bateau = new Bateau();
                bateau = bateau.findById(connex, idBateau);
                Escale escale = new Escale(bateau, dateDebut, null);
                HttpSession session = request.getSession();
                session.setAttribute("createEscale", escale);
                request.setAttribute("idCreate",1);
 
            }
            else if (request.getParameter("quai") != null) {
                HttpSession session = request.getSession();
                Escale escale = (Escale)session.getAttribute("createEscale");
                Quai quai = new Quai();
                quai = quai.findById(connex, Integer.valueOf(request.getParameter("quai")));
                escale.setQuai(quai);
                ChronoChangeQuai chrono = new ChronoChangeQuai(escale, quai, escale.getDateDebut(), null);
                session.invalidate();
                escale.create(connex);
                chrono.create(connex);
            }
            else {
                new Exception("une valeur est nulle");
            }
            
            connex.close(); 
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }  
        doGet(request, response);
    }

}
