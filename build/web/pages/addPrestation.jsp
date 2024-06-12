<%@page import="model.gestionFinanciere.Escale"%>
<%@page import="model.profil.Users"%>
<%@page import="model.gestionFinanciere.TypePrestation"%>
<%@page import="model.profil.Profil"%>
<%@page import="model.gestionBateau.Bateau"%>
<%@page import="model.gestionQuai.Quai"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
         <% if(request.getAttribute("escale") != null) { 
             Escale escale = (Escale)request.getAttribute("escale");
         %>
          <div class="card">
               <div class="card-body">
                    <h4 class="card-title">Ajouter un nouveau prestation</h4>
                    <form class="forms-sample" action="addPrestation" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Choisir profil :</label>
                            <select class="form-control" id="exampleSelectGender" name="profil">
                                <% if(request.getAttribute("profils") != null) { 
                                ArrayList<Users> profils = (ArrayList<Users>)request.getAttribute("profils");
                                for(int i = 0; i < profils.size(); i++) { %>
                                    <option value=<%=profils.get(i).getId() %>><%=profils.get(i).getNom() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleSelectGender">Prestations</label>
                            <select class="form-control" id="exampleSelectGender" name="prestation">
                                <% if(request.getAttribute("typePrestations") != null) { 
                                ArrayList<TypePrestation> types = (ArrayList<TypePrestation>)request.getAttribute("typePrestations");
                                for(int i = 0; i < types.size(); i++) { %>
                                    <option value=<%=types.get(i).getId() %>><%=types.get(i).getDesignation() %></option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date debut</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_debut">
                             <label for="exampleInputUsername1">Heure debut</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_debut">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUsername1">Date fin</label>
                            <input type="date" class="form-control" id="exampleInputUsername1" name="date_fin">
                             <label for="exampleInputUsername1">Heure fin</label>
                            <input type="time" class="form-control" id="exampleInputUsername1" name="heure_fin">
                        </div>
                       
                        <button type="submit" class="btn btn-gradient-primary me-2">Enregistrer</button>
                    </form>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } %>
               </div>
          </div>
        <p>
            <a href="detailsEscal?idEscale=<%=escale.getId() %>">
                <button type="submit" class="btn btn-gradient-primary me-2">Retour</button>
            </a>  
        </p>
     </div>
    <% } %>
</div>
<%@include file="footer.jsp" %>