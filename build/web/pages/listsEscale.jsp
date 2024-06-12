<%@page import="model.gestionFinanciere.Escale"%>
<%@page import="model.gestionBateau.Prevision"%>
<%@page import="java.util.ArrayList , model.employe.* , model.*, model.service.* "%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-lg-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
               <%
                    if(request.getAttribute("error")!=null){
                    %>
                    <div class="alert alert-success" role="alert">
                         <%=request.getAttribute("error")%>
                    </div>
               <% }
               %>
                    <h4 class="card-title">Liste des escales</h4>
                    <table class="table">
                         <thead>
                              <tr>
                                    <td>Reference</td>
                                    <td>Bateau</td>
                                    <td>Date debut</td>
                                    <td>Date fin</td>
                                    <td>Etat</td>
                                    <td></td>
                              </tr>
                         </thead>
                         <tbody>
                              
                           <% if(request.getAttribute("listsEscale") != null) { 
                            ArrayList<Escale> escales = (ArrayList<Escale>)request.getAttribute("listsEscale");
                            for(int i = 0; i < escales.size(); i++) { %>
                        <tr>
                            <td><%=escales.get(i).getReference() %></td>
                            <td><%=escales.get(i).getBateau().getNom() %></td>
                            <td><%=escales.get(i).getDateDebut() %></td>
                            <td><%=escales.get(i).getDateFin() %></td>
                            <td><%=escales.get(i).getEtatLettre() %></td>
                            <td><a href="detailsEscal?idEscale=<%=escales.get(i).getId()%>">Voir details</a></td>
                        </tr>
                       <% } } %>
                         </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>