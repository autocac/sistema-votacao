<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votações - Sistema Votação</title>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Lista Votações</h1>
		<h2>${msg}</h2>
	
		<table border="1" align="center">
			<thead>
				<tr>
					<th>ID</th>
					<th>Descrição</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="votacao" items="${votacoes}" >
					<tr>
						<td align="center">
							<a href="/SistemaVotacao/EditarVotacaoServlet?acao=detalhe&idVotacao=${votacao.id}">
								${votacao.id}
							</a>
						</td>
						<td><c:out value="${votacao.descricao}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td rowspan="2">----Selecione uma votacao para alterar----</td>
				</tr>
			</tfoot>
		</table>	
	</body>
</html>