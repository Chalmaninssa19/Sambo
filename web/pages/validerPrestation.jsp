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
                    <h4 class="card-title">Valider prestation</h4>
                    <% if(request.getAttribute("prestation") != null) { 
                        Prestation prestation = (Prestation)request.getAttribute("prestation");
                    %>
                    <form class="forms-sample" action="validationPrestation" method="POST">
                        <div class="form-group">
                            <label for="exampleSelectGender">Veuillez choisir votre profil pour pouvoir valider la prestation :</label>
                            <select class="form-control" id="exampleSelectGender" name="profil">
                                <% if(request.getAttribute("users") != null) { 
                                ArrayList<Users> users = (ArrayList<Users>)request.getAttribute("users");
                                for(int i = 0; i < users.size(); i++) { %>
                                <option value=<%=users.get(i).getId() %>><%=users.get(i).getNom() %></option>
                                <% } } %>
                            </select>
                            <input type="hidden" name="prestation" value="<%=prestation.getId() %>"/>
                        </div>
                        <button type="submit" class="btn btn-gradient-primary me-2">Valider</button>
                    </form>
                    <% } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>