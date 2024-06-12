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
                    <h4 class="card-title">Fin escale</h4>
                    <form class="forms-sample" action="finEscale" method="POST">
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
     </div>
</div>
<%@include file="footer.jsp" %>