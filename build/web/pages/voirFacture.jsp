<%@page import="model.gestionFinanciere.Facture"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.gestionFinanciere.Escale"%>
<%@include file="header.jsp" %>
<% if (request.getAttribute("montantEuro") != null && request.getAttribute("escale") != null && request.getAttribute("dateNow") != null && request.getAttribute("montantTotal") != null) { 
    Escale escale = (Escale)request.getAttribute("escale");
%>
        <h1 style="text-align: center;margin-bottom:10%;">Fiche de Facture</h1>
        <p style="margin-left:15%; width:70%;"><h4>Reference : <%=escale.getReference() %></h4></p>
        <p style="margin-left:15%;  width:70%;"><h4>Bateau : <%=escale.getBateau().getNom() %></h4></p>
        <p style="margin-left:15%; width:70%;"><h4>Date facturation : <%=request.getAttribute("dateNow") %></h4></p>


        <div class="container text-center">
            
            <div class="row">
                <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                   Reference
                </div>
                <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                   Prestation
                </div>
                <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                    Quai
                </div>
                <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                    Date debut
                </div>
                <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                    Duree
                </div>
                 <div class="col"  style="background-color: aqua; border: 1px solid rgb(00,00,00);">
                    Montant
                </div>
            </div>
            <% if(request.getAttribute("factures") != null) {
                ArrayList<Facture> factures = (ArrayList<Facture>)request.getAttribute("factures");
                for(int i = 0; i < factures.size(); i++) { %>
                <div class="row">
                    <div class="col"  style="border: 1px solid rgb(00,00,00);">
                    <%= factures.get(i).getPrestation().getReference() %>
                    </div>
                    
                    <div class="col" style="border: 1px solid rgb(00,00,00);">
                    <%=factures.get(i).getPrestation().getTypePrestation().getDesignation() %>
                    </div>
                    <div class="col" style="border: 1px solid rgb(00,00,00);">
                    <%=factures.get(i).getPrestation().getQuai().getNom() %>
                    </div>
                    <div class="col" style="border: 1px solid rgb(00,00,00);">
                        <%=factures.get(i).getPrestation().getDateDebut() %>
                    </div>
                    <div class="col" style="border: 1px solid rgb(00,00,00);">
                        <%=factures.get(i).getPrestation().getDuree() %>
                    </div>
                    <div class="col" style="border: 1px solid rgb(00,00,00);">
                        <%=factures.get(i).getPrestation().getMontant() %>
                    </div>
                </div>
            <%    } }
            %>
        </div>
        <p style="margin-left:15%; width:70%;"><h4>Montant total : <%=request.getAttribute("montantTotal") %> ariary</h4></p>
        <p style="margin-left:15%; width:70%;"><h4>En euro : <%=request.getAttribute("montantEuro") %> EUR</h4></p>
        <p>
            <a href="detailsEscal?idEscale=<%=escale.getId() %>">
                <button type="submit" class="btn btn-gradient-primary me-2">Retour</button>
            </a>  
        </p>
<% } %>
<%@include file="footer.jsp" %>