<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votações - Sistema Votação</title>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Votações</h1>
		<h2>${msg}</h2>
		
		<table align="center">
			<tr>
				<td>
					<ul>
						<c:forEach var="votacao" items="${votacoes}">
							<li>
								<a href="/SistemaVotacao/DetalheVotacaoServlet?idVotacao=${votacao.id}">${votacao.id} - ${votacao.descricao}</a>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</table>
	</body>
</html>