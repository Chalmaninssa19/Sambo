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
                    <h4 class="card-title">Liste previsions</h4>
                    <table class="table">
                         <thead>
                              <tr>
                                    <td>Date</td>
                                    <td>Ref</td>
                                    <td>Bateau</td>
                                    <td>Date depart</td>
                                    <td>Date arrive</td>
                                    <td>Duree</td>
                              </tr>
                         </thead>
                         <tbody>
                              
                        <% if(request.getAttribute("listPrevision") != null) { 
                            ArrayList<Prevision> previsions = (ArrayList<Prevision>)request.getAttribute("listPrevision");
                            for(int i = 0; i < previsions.size(); i++) { %>
                        <tr>
                            <td></td>
                             <td><%=previsions.get(i).getId() %></td>
                             <td><%=previsions.get(i).getBateau().getNom() %></td>
                             <td><%=previsions.get(i).getDateDebut() %></td>
                             <td><%=previsions.get(i).getDateFin() %></td>
                             <td><%=previsions.get(i).getDureeEscale() %></td>
                         </tr>
                        <% } } %>
                         </tbody>
                    </table>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>