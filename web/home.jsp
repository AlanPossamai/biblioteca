<%--
    Document   : home
    Created on : 07/10/2018, 14:23:40
    Author     : alanp
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:import url="inc/cabecalho.inc.jsp">
    <c:param name="pageName" value="Cadastro" />
</c:import>

<%@page import="br.ifrs.vaccinare.inc.ControleAcesso"%>
<%
	ControleAcesso.verificar(request, response);
%>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="jumbotron mt-3">
                <h2>Bem vindo ao Vaccinare</h2>
                <div>
                    Vaccinare é um sistema simples e intuitivo para gestão de vacinações de crianças.
                </div>
            </div>
        </div>
    </div>
</div>

<div class="wait"></div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
