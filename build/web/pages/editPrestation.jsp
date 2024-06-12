<%@page import="model.profil.Users"%>
<%@page import="model.gestionFinanciere.Prestation"%>
<%@page import="model.gestionFinanciere.TypePrestation"%>
<%@page import="model.profil.Profil"%>
<%@page import="model.gestionBateau.Bateau"%>
<%@page import="model.gestionQuai.Quai"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">            
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               <div class="card-body">
                    <h4 class="card-title">Modifier prestation</h4>
                    <% if(request.getAttribute("prestation") != null) { 
                        Prestation prestation = (Prestation)request.getAttribute("prestation");
                    %>
                    <form class="forms-sample" action="editPrestation" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Choisir profil :</label>
                            <select class="form-control" id="exampleSelectGender" name="profil">
                                <% if(request.getAttribute("profils") != null) { 
                                ArrayList<Users> users = (ArrayList<Users>)request.getAttribute("profils");
                                for(int i = 0; i < users.size(); i++) { %>
                                <option value=<%=users.get(i).getId() %>><%=users.get(i).getNom() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Prestations</label>
                            <select class="form-control" id="exampleSelectGender" name="prestation">
                                <option value=<%=prestation.getId() %>><%=prestation.getTypePrestation().getDesignation() %></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Quai</label>
                            <select class="form-control" id="exampleSelectGender" name="quai">
                                <option  value=<%=prestation.getQuai().getId() %>><%=prestation.getQuai().getNom() %></option>
                            </select>       
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Montant</label>
                            <input type="number" step="0.01" class="form-control" id="exampleInputUsername1" name="montant" value="<%=prestation.getMontant() %>">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date debut</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_debut" value="<%=request.getAttribute("dateDebut") %>">
                             <label for="exampleInputUsername1">Heure debut</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_debut">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date fin</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_fin" value="<%=request.getAttribute("dateFin") %>">
                             <label for="exampleInputUsername1">Heure fin</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_fin">
                        </div>
                        <input type="hidden" class="form-control" id="exampleInputUsername1" name="idPrestation" value="<%=prestation.getId() %>">
                       
                        <button type="submit" class="btn btn-gradient-primary me-2">Modifier</button>
                    </form>
                    <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>