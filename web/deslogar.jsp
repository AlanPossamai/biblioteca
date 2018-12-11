<%--
    Document   : controleAcesso
    Created on : 07/10/2018, 15:46:31
    Author     : alanp
--%>

<%@page import="br.ifrs.vaccinare.inc.ControleAcesso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
        ControleAcesso.logout(request, response);
%>
