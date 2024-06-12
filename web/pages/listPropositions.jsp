<%@page import="model.gestionQuai.PrevisionDecale"%>
<%@page import="utilitaire.Util"%>
<%@page import="model.gestionQuai.Proposition"%>
<%@page import="model.gestionBateau.Prevision"%>
<%@page import="java.util.ArrayList , model.employe.* , model.*, model.service.* "%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-lg-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
               <%
                    if(request.getAttribute("message")!=null){
                    %>
                    <div class="alert alert-success" role="alert">
                         <%=request.getAttribute("message")%>
                    </div>
               <% }
               %>
                    <h4 class="card-title">Liste propositions</h4>
                    <table class="table">
                         <thead>
                            <tr>
                                <th>Ref</th>
                                <th>Bateau</th>
                                <th>Date debut</th>
                                <th>Date fin</th>
                                <th>Duree d'attente</th>
                                <th>Quai</th>
                                <th>Montant</th>
                                <th></th>
                                <th></th>
                            </tr>
                         </thead>
                         <tbody>
                              
                           <% if(request.getAttribute("listPropositions") != null) { 
                            ArrayList<Proposition> propositions = (ArrayList<Proposition>)request.getAttribute("listPropositions");
                            for(int i = 0; i < propositions.size(); i++) { %>
                        <tr>
                            <td><%=propositions.get(i).getReference() %></td>
                            <td><%=propositions.get(i).getPrevision().getBateau().getNom() %></td>
                            <td><%=propositions.get(i).getPrevision().getDateDebut() %></td>
                            <td><%=propositions.get(i).getPrevision().getDateFin() %></td>
                            <td><%=propositions.get(i).getDureeAttente() %></td>
                            <td><%=propositions.get(i).getQuai().getNom() %></td>
                            <td><%=Util.formattedNumber(propositions.get(i).getMontant()) %></td>
                            <td><a href='#'>Modifier</a></td>
                            <td><a href="#">Valider</a></td>
                        </tr>
                       <% } } %>
                         </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>