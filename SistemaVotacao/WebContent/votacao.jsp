<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votações - Sistema Votação</title>
	</head>
	<body>
		<c:if test="${user.tipo eq Usuario.Tipo.ADMINISTRADOR}">
			<table border="1">
				<tr>
					<td>
						<a href="/NovaVotacaoServlet">Nova Votacao</a>
					</td>
					<td>
						<a href="/NovoEleitorServlet">Novo Eleitor</a>
					</td>
				</tr>
			</table>
		</c:if>
		<h1>Votações - Sistema Votação</h1>
		<h2>${msg}</h2>
		
		<ul>
			<c:forEach var="votacao" items="${votacoes}">
				<li>
					<a href="ListaCandidatosVotacaoServlet?idVotacao=${votacao.id}">${votacao.id} - ${votacao.descricao}</a>
				</li>
			</c:forEach>
		</ul>
	</body>
</html>