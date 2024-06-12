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
public class ControllerInsertPrevision extends HttpServlet {

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
                    request.setAttribute("error", request.getAttribute("error"));
                }
                Connection connex = GConnection.getSimpleConnection();
                Bateau bateau = new Bateau();
                ArrayList<Bateau> listsBateau = bateau.all(connex);
                request.setAttribute("listsBateau", listsBateau);
                connex.close();
        } catch (Exception exe) {
            //exe.printStackTrace();
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/addPrevision.jsp");
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
            if(request.getParameter("bateau") != null && request.getParameter("date_depart") != null &&  request.getParameter("heure_depart") != null && request.getParameter("date_arrive") != null && request.getParameter("heure_arrive") != null) {
                Connection connex = GConnection.getSimpleConnection();
                Util util = new Util();          
                Integer idBateau = Integer.valueOf(request.getParameter("bateau"));
                Timestamp dateDepart = util.conversIntimestamp(request.getParameter("date_depart"), request.getParameter("heure_depart"));
                Timestamp dateArrive = util.conversIntimestamp(request.getParameter("date_arrive"), request.getParameter("heure_arrive")); 
                Bateau bateau = new Bateau();
                bateau = bateau.findById(connex, idBateau);
                Prevision prevision = new Prevision(dateDepart, dateArrive, bateau);
                Proposition proposition = new Proposition();
                prevision.create(connex);

                connex.close();  
            }
            else {
                new Exception("une valeur est nulle");
            }
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
            //RequestDispatcher dispat = request.getRequestDispatcher("./pages/addPrevision.jsp");
            //dispat.forward(request, response);
        }  
        doGet(request, response);
    }

}
