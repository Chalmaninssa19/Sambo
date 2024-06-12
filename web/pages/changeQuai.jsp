<%@page import="model.gestionBateau.Bateau"%>
<%@page import="model.gestionQuai.Quai"%>
<%@page import="java.util.ArrayList"%>
<%@include file="header.jsp" %>
<div class="row">
    <div class="col-md-6 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">Vous etes en cours de changement de quai</h4>
                <form class="forms-sample" action="changeQuai" method="POST">
                <div class="form-group">
                    <label for="exampleSelectGender">Quai</label>
                    <select class="form-control" id="exampleSelectGender" name="quai">
                        <%  if(request.getAttribute("listsQuai") != null) { 
                                ArrayList<Quai> quais = (ArrayList<Quai>)request.getAttribute("listsQuai");
                                for(int i = 0; i < quais.size(); i++) { %>
                                    <option value=<%=quais.get(i).getId() %>><%=quais.get(i).getNom() %></option>
                        <% } } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleInputUsername1">Date debut</label>
                    <input type="date" class="form-control" id="exampleInputUsername1" name="date_debut">
                    <label for="exampleInputUsername1">Heure debut</label>
                    <input type="time" class="form-control" id="exampleInputUsername1" name="heure_debut">
                </div>
                <button type="submit" class="btn btn-gradient-primary me-2">Changer</button>
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