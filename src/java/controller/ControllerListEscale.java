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
import model.gestionFinanciere.Escale;

/**
 *
 * @author Chalman
 */
public class ControllerListEscale extends HttpServlet {

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
            
            Escale escale = new Escale();
            ArrayList<Escale> listEscale = escale.allTrieByIdDesc(conn);
            request.setAttribute("listsEscale", listEscale);
            
            conn.close();
        } catch (Exception exe) {
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/listsEscale.jsp");
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
            request.setAttribute("error", exe.getMessage());
            doGet(request, response);
        }  
        doGet(request, response);
    }

}
