<%@page import="model.profil.Users"%>
<%@page import="model.gestionFinanciere.TypePrestation"%>
<%@page import="model.profil.Profil"%>
<%@page import="model.gestionBateau.Bateau"%>
<%@page import="model.gestionQuai.Quai"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
     <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
               <div class="card-body">
                   <% if(request.getAttribute("id") != null) { %>
                    <h4 class="card-title">Identifier-vous en tant que facturation</h4>
                    <form class="forms-sample" action="identify" method="POST">
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
                        <input type="hidden" value="<%=request.getAttribute("id") %>" name="id"/>
                        <button type="submit" class="btn btn-gradient-primary me-2">S'identifier</button>
                    </form>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-success" role="alert" style="color: red">
                        <%=request.getAttribute("error") %>
                    </div>
                <% } } %>
               </div>
          </div>
     </div>
</div>
<%@include file="footer.jsp" %>