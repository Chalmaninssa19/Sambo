<%@page import="model.gestionBateau.Bateau"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Inserer une prevision</h4>
                    <form class="forms-sample" action="insertPrevision" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Bateau</label>
                            <select class="form-control" id="exampleSelectGender" name="bateau">
                                <% if(request.getAttribute("listsBateau") != null) { 
                                ArrayList<Bateau> bateaux = (ArrayList<Bateau>)request.getAttribute("listsBateau");
                                for(int i = 0; i < bateaux.size(); i++) { %>
                                    <option value=<%=bateaux.get(i).getId() %>><%=bateaux.get(i).getNom() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date debut</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_depart">
                             <label for="exampleInputUsername1">Heure debut</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_depart">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date fin</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_arrive">
                             <label for="exampleInputUsername1">Heure fin</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_arrive">
                        </div>
                        <button type="submit" class="btn btn-gradient-primary me-2">Ajouter</button>
                    </form>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>